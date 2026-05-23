package com.poq.global.scheduler;

import com.poq.domain.assignment.service.DailyAssignmentService;
import com.poq.domain.archive.service.MonthlyArchiveService;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final PetService petService;
    private final DailyAssignmentService assignmentService;
    private final MonthlyArchiveService archiveService;

    /**
     * 일일 질문 배정 배치 스케줄러 (매일 자정 실행)
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void assignDailyQuestions() {
        log.info("일일 질문 배정 스케줄러 시작");
        List<Pet> pets = petService.getAllPets();
        LocalDate today = LocalDate.now();

        for (Pet pet : pets) {
            try {
                assignmentService.assignTodayQuestionForPet(pet, today);
            } catch (Exception e) {
                log.error("반려동물 {}의 질문 배정 실패: {}", pet.getId(), e.getMessage());
            }
        }
        log.info("일일 질문 배정 스케줄러 완료 (대상 펫 수: {})", pets.size());
    }

    /**
     * 월간 아카이브 요약 집계 배치 스케줄러 (매월 1일 자정 실행)
     */
    @Scheduled(cron = "0 0 0 1 * *")
    public void compileMonthlyArchives() {
        log.info("월간 아카이브 스케줄러 시작");
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        String monthStr = lastMonth.toString(); // YYYY-MM
        List<Pet> pets = petService.getAllPets();

        for (Pet pet : pets) {
            try {
                archiveService.compileMonthlyArchive(pet.getId(), monthStr);
            } catch (IllegalArgumentException e) {
                log.debug("반려동물 {}의 {}월 기록이 없어 아카이브 생성 건너뜀", pet.getId(), monthStr);
            } catch (Exception e) {
                log.error("반려동물 {}의 월간 아카이브 생성 중 예외 발생: {}", pet.getId(), e.getMessage());
            }
        }
        log.info("월간 아카이브 스케줄러 완료");
    }
}
