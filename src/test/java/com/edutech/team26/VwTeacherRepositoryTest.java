package com.edutech.team26;

import com.edutech.team26.model.VwTeacher;
import com.edutech.team26.repository.VwTeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class VwTeacherRepositoryTest {

    @Autowired
    private VwTeacherRepository vwTeacherRepository;

    @Test
    public void testFindAll() {
        List<VwTeacher> teachers = vwTeacherRepository.findAll();
        for (VwTeacher teacher : teachers) {
            System.out.println("선생님 : " + teacher);
        }
    }
}
