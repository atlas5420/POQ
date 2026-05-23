package com.poq.domain.diary.repository;

import com.poq.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// 💡 DiaryRepositoryCustom을 상속
public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {
    List<Diary> findByPetIdOrderByDiaryDateDesc(Long petId);
}