package com.edutech.team26.biz;

import com.edutech.team26.domain.Lecture;
import com.edutech.team26.repository.LectureRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private LectureRepository lectureRepository;

    //@Scheduled(cron = "0 0 0 * * *") //오전 0시마다 실행됨 -> 테스트 끝나고 다시 설정
    //@Scheduled(fixedDelay = 1000) //1초마다
    @PostConstruct  //테스트용
    public void lectureUpdate() {

        //LocalDateTime currentDate = LocalDateTime.now();
        LocalDate currentDate = LocalDate.now();
        List<Lecture> lectures = lectureRepository.findAll();
        System.out.println("현재날짜>>>>>>>" + currentDate);
        System.out.println("뭐야>>>>>>>" + lectures);

        for (Lecture lecture : lectures) {
         LocalDate startStudyLocalDate = LocalDate.parse(lecture.getStartStudyDate(), DateTimeFormatter.ISO_LOCAL_DATE);
         LocalDate endStudyLocalDate = LocalDate.parse(lecture.getEndStudyDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            //LocalDate startStudyLocalDate = LocalDate.parse(startStudyDate, DateTimeFormatter.ISO_LOCAL_DATE);
            //LocalDate endStudyLocalDate = LocalDate.parse(endEnrolmentDate, DateTimeFormatter.ISO_LOCAL_DATE);

            // Update lectureAct based on the conditions
        if (startStudyLocalDate.isAfter(currentDate)) {
            lecture.updateLectureAct(1);// 강의예정
        } else if ((startStudyLocalDate.isEqual(currentDate) || startStudyLocalDate.isBefore(currentDate)) && endStudyLocalDate.isAfter(currentDate)) {
            lecture.updateLectureAct(2); // 강의진행중
        } else if (endStudyLocalDate.isBefore(currentDate)) {
            lecture.updateLectureAct(3); // 강의종료
        }

            lectureRepository.save(lecture);
        }



    }

}