CREATE DATABASE edutech DEFAULT CHARACTER SET UTF8MB4;

USE edutech;

-- view
CREATE VIEW studentList AS (SELECT m.mno AS mno, m.mod_date AS modDate, m.reg_date AS regDate, m.addr AS addr, m.addr_detail AS addr_detail, m.email AS email, m.phone AS phone, m.user_name AS userName, m.zipcode AS zipcode, m.user_status AS userStatus, s.student_no AS studentNo, s.entrance_yn AS entranceYn, s.lecture_no AS lectureNo FROM member m RIGHT JOIN student s ON m.mno = s.mno);
CREATE VIEW teacherList AS (SELECT m.mno AS mno, m.mod_date AS modDate, m.reg_date AS regDate, m.addr AS addr, m.addr_detail AS addr_detail, m.email AS email, m.phone AS phone, m.user_name AS userName, m.zipcode AS zipcode, m.user_status AS userStatus, t.teacher_no AS teacherNo, t.active_date AS activeDate, t.active_yn AS activeYn, t.file_origin_nm AS fileOriginNm, t.file_save_nm AS fileSaveNm, t.intro AS intro, t.status AS status FROM member m RIGHT JOIN teacher t ON m.mno = t.mno)