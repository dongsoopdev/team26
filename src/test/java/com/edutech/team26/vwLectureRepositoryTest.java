package com.edutech.team26;

import com.edutech.team26.biz.LectureService;
import com.edutech.team26.domain.VwLecture;

import com.edutech.team26.repository.VwLectureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class vwLectureRepositoryTest {

    @Autowired
    private VwLectureRepository vwLectureRepository;

    @Autowired
    private LectureService lectureService;

    @Test
    public void testFindAll() {
        List<VwLecture> teachers = vwLectureRepository.findAll();
        for (VwLecture teacher : teachers) {
            System.out.println("선생님 : " + teacher);
        }
    }

    @Test
    public void testGetAllTeacherViews() {
        // 실행 전에 TeacherView 테이블에 테스트용 데이터가 있어야 합니다.

        // 테스트용 데이터를 출력
        lectureService.vwFindAll().forEach(teacherView -> {
            System.out.println("Teacher ID: " + teacherView.getMno());
            // 다른 필드들에 대한 출력 추가
        });
    }
}
