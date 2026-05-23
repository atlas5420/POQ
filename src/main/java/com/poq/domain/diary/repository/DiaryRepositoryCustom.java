package com.poq.domain.diary.repository;

import com.poq.domain.diary.entity.Diary;
import java.util.List;

public interface DiaryRepositoryCustom {
    // 💡 펫 ID와 함께 키워드(선택)가 있으면 동적 검색하는 메서드 정의
    List<Diary> searchDiaries(Long petId, String keyword);
}