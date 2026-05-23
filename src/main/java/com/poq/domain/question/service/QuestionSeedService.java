package com.poq.domain.question.service;

import com.poq.domain.question.entity.Question;
import com.poq.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionSeedService {

    private final QuestionRepository questionRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void seedQuestions() {
        if (questionRepository.count() == 0) {
            questionRepository.save(Question.builder()
                    .category("습관")
                    .text("오늘 가장 많이 취했던 수면 자세나 특별히 선호했던 장소는 어디인가요?")
                    .speciesType(null)
                    .active(true)
                    .build());

            questionRepository.save(Question.builder()
                    .category("산책")
                    .text("오늘 산책 중 가장 오랫동안 냄새를 킁킁대며 탐색했던 장소나 물건은 무엇이었나요?")
                    .speciesType("강아지")
                    .active(true)
                    .build());

            questionRepository.save(Question.builder()
                    .category("소리")
                    .text("오늘 냥냥거리거나 골골송을 부르는 등 어떤 소리 소통을 많이 시도했나요?")
                    .speciesType("고양이")
                    .active(true)
                    .build());

            questionRepository.save(Question.builder()
                    .category("표정")
                    .text("오늘 눈을 마주쳤을 때 아이가 어떤 감정을 말하고 있는 듯한 표정이었나요?")
                    .speciesType(null)
                    .active(true)
                    .build());

            questionRepository.save(Question.builder()
                    .category("건강")
                    .text("오늘 발바닥 털, 빗질 상태, 눈곱 등 아이의 구체적인 청결 상태는 어땠나요?")
                    .speciesType(null)
                    .active(true)
                    .build());
        }
    }
}
