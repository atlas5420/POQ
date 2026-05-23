package com.poq.domain.diary.repository;

import com.poq.domain.diary.entity.Diary;
import com.poq.domain.diary.entity.QDiary;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Diary> searchDiaries(Long petId, String keyword) {
        QDiary diary = QDiary.diary;

        return queryFactory
                .selectFrom(diary)
                .where(
                        diary.pet.id.eq(petId),
                        containsKeyword(keyword)
                )
                .orderBy(diary.diaryDate.desc())
                .fetch();
    }

    // 💡 키워드가 없으면(null 또는 공백) where절에서 무시되도록 처리하는 핵심 메서드
    private BooleanExpression containsKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null; 
        }
        return QDiary.diary.title.contains(keyword)
                .or(QDiary.diary.content.contains(keyword));
    }
}