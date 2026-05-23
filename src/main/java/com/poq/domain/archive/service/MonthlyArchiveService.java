package com.poq.domain.archive.service;

import com.poq.domain.archive.entity.MonthlyArchive;
import com.poq.domain.archive.repository.MonthlyArchiveRepository;
import com.poq.domain.entry.entity.Entry;
import com.poq.domain.entry.service.EntryService;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MonthlyArchiveService {

    private final MonthlyArchiveRepository archiveRepository;
    private final EntryService entryService;
    private final PetService petService;

    /**
     * 특정 반려동물의 전체 월간 아카이브 조회
     */
    public List<MonthlyArchive> getArchivesByPetId(UUID petId) {
        return archiveRepository.findByPetIdOrderByMonthDesc(petId);
    }

    /**
     * 특정 반려동물의 지정된 월(YYYY-MM) 아카이브 집계 및 생성
     */
    @Transactional
    public MonthlyArchive compileMonthlyArchive(UUID petId, String month) {
        Pet pet = petService.getPetById(petId);
        YearMonth yearMonth = YearMonth.parse(month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        // 1. 해당 월에 작성된 모든 일기 조회
        List<Entry> entries = entryService.getEntriesByPetIdAndDateRange(petId, start, end);
        if (entries.isEmpty()) {
            throw new IllegalArgumentException(month + "월에 작성된 기록이 존재하지 않아 아카이브를 생성할 수 없습니다.");
        }

        // 2. 태그 빈도 분석
        Map<String, Integer> tagCounts = new HashMap<>();
        String coverPhotoUrl = null;

        for (Entry entry : entries) {
            // 태그 수집
            if (entry.getTags() != null && !entry.getTags().isBlank()) {
                String[] tagsArray = entry.getTags().split(",");
                for (String tag : tagsArray) {
                    String trimmedTag = tag.trim();
                    if (!trimmedTag.isEmpty()) {
                        tagCounts.put(trimmedTag, tagCounts.getOrDefault(trimmedTag, 0) + 1);
                    }
                }
            }
            // 대표 사진 선택 (가장 최근에 등록된 사진이 있는 일기의 사진)
            if (coverPhotoUrl == null && entry.getPhotoUrl() != null && !entry.getPhotoUrl().isBlank()) {
                coverPhotoUrl = entry.getPhotoUrl();
            }
        }

        // 가장 많이 사용된 상위 3개 태그 추출
        List<String> topTags = tagCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();

        // 3. 대표 요약 문구 생성
        String tagsSummary = topTags.isEmpty() ? "지정된 해시태그 없음" : topTags.stream().map(t -> "#" + t).collect(Collectors.joining(", "));
        String summaryText = String.format(
                "%d년 %02d월 한 달 동안 %s(이)와 총 %d개의 관찰 답변 기록을 완성했습니다. " +
                "이달의 핵심 감정 및 키워드는 [%s] 이며, 깊은 교감을 통해 반려동물의 다양한 매력을 기록한 특별한 한 달이었습니다.",
                yearMonth.getYear(), yearMonth.getMonthValue(), pet.getName(), entries.size(), tagsSummary
        );

        final String finalCoverPhotoUrl = coverPhotoUrl;

        // 4. 기존 아카이브가 있으면 업데이트, 없으면 신규 저장
        MonthlyArchive archive = archiveRepository.findByPetIdAndMonth(petId, month)
                .map(existing -> {
                    existing.update(summaryText, finalCoverPhotoUrl);
                    return archiveRepository.save(existing);
                })
                .orElseGet(() -> MonthlyArchive.builder()
                        .user(pet.getUser())
                        .pet(pet)
                        .month(month)
                        .summaryText(summaryText)
                        .coverPhotoUrl(finalCoverPhotoUrl)
                        .build());

        return archiveRepository.save(archive);
    }
}
