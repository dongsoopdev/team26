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
    @PostConstruct  //테스트용 -> 서버 돌릴 때마다 실행됨
    public void lectureUpdate() {

        //LocalDateTime currentDate = LocalDateTime.now();
        LocalDate currentDate = LocalDate.now();
        List<Lecture> lectures = lectureRepository.findAll();
        System.out.println("현재날짜>>>>>>>" + currentDate);
        System.out.println("뭐야>>>>>>>" + lectures);

        for (Lecture lecture : lectures) {
            LocalDate startEnrolmentDate = LocalDate.parse(lecture.getStartEnrolmentDate(), DateTimeFormatter.ISO_LOCAL_DATE); //수강신청 시작
            LocalDate endEnrolmentDate = LocalDate.parse(lecture.getEndEnrolmentDate(), DateTimeFormatter.ISO_LOCAL_DATE);    // 수강신청 마감

            int curnum = lecture.getLectureCurnum(); //현재인원
            int minnum = lecture.getLectureMinnum(); //최소인원
            int maxnum = lecture.getLectureMaxnum(); //최대인원

            LocalDate startStudyLocalDate = LocalDate.parse(lecture.getStartStudyDate(), DateTimeFormatter.ISO_LOCAL_DATE); //강의 시작
            LocalDate endStudyLocalDate = LocalDate.parse(lecture.getEndStudyDate(), DateTimeFormatter.ISO_LOCAL_DATE);     //강의 마감
            //LocalDate startStudyLocalDate = LocalDate.parse(startStudyDate, DateTimeFormatter.ISO_LOCAL_DATE);
            //LocalDate endStudyLocalDate = LocalDate.parse(endEnrolmentDate, DateTimeFormatter.ISO_LOCAL_DATE);


            if(lecture.getLectureAct() != 5) {
                // 강의 등록된 후 수강신청 기간 동안 상태
                if (startStudyLocalDate.isAfter(currentDate)) {
                    lecture.updateLectureAct(1); //강의예정
                }
                if ((startStudyLocalDate.isEqual(currentDate) || startStudyLocalDate.isBefore(currentDate)) && endStudyLocalDate.isAfter(currentDate) && curnum >= minnum) {
                    lecture.updateLectureAct(2); // 강의진행중
                } else if (endStudyLocalDate.isBefore(currentDate) && curnum >= minnum) {
                    lecture.updateLectureAct(3); // 강의종료
                } else if (endEnrolmentDate.isBefore(currentDate) && curnum < minnum) { // 수강마감기간 끝난 후 처리 됨
                    lecture.updateLectureAct(4); //강의폐강
                }

            }

            lectureRepository.save(lecture);
        }


    }

}