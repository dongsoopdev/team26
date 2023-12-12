CREATE DATABASE edutech DEFAULT CHARACTER SET UTF8MB4;

USE edutech;

-- view
CREATE VIEW studentList AS (SELECT m.mno AS mno, m.mod_date AS modDate, m.reg_date AS regDate, m.addr AS addr, m.addr_detail AS addr_detail, m.email AS email, m.phone AS phone, m.user_name AS userName, m.zipcode AS zipcode, m.user_status AS userStatus, s.student_no AS studentNo, s.entrance_yn AS entranceYn, s.lecture_no AS lectureNo FROM member m RIGHT JOIN student s ON m.mno = s.mno);
-- CREATE VIEW teacherList AS (SELECT m.mno AS mno, m.mod_date AS modDate, m.reg_date AS regDate, m.addr AS addr, m.addr_detail AS addr_detail, m.email AS email, m.phone AS phone, m.user_name AS userName, m.zipcode AS zipcode, m.user_status AS userStatus, t.teacher_no AS teacherNo, t.active_date AS activeDate, t.active_yn AS activeYn, t.file_origin_nm AS fileOriginNm, t.file_save_nm AS fileSaveNm, t.intro AS intro, t.status AS status FROM member m RIGHT JOIN teacher t ON m.mno = t.mno)


-- 선생님 view
drop VIEW teacherlist;
CREATE VIEW teacherlist AS
SELECT
    m.mno AS mno,
    m.mod_date AS mod_date,
    m.reg_date AS reg_date,
    m.addr AS addr,
    m.addr_detail AS addr_detail,
    m.email AS email,
    m.phone AS phone,
    m.user_name AS user_name,
    m.zipcode AS zipcode,
    m.user_status AS user_status,
    t.teacher_no AS teacher_no,
    t.active_date AS active_date,
    t.active_yn AS active_yn,
    t.file_origin_nm AS file_origin_nm,
    t.file_save_nm AS file_save_nm ,
    t.intro AS intro,
    t.status AS status
FROM
    member m
        RIGHT JOIN
    teacher t ON m.mno = t.mno;




-- 수강신청내역 view (예진 view)
drop VIEW courselist;
CREATE VIEW courselist AS
SELECT
    m.mno AS mno,
    m.mod_date AS mod_date,
    m.reg_date AS reg_date,
    m.user_name AS user_name,
    m.user_status AS user_status,

    t.teacher_no AS teacher_no,
    t.active_date AS active_date,
    t.active_yn AS active_yn,
    t.intro AS intro,
    t.status AS STATUS,

    l.lecture_no AS lecture_no,
    l.lecture_name AS lecture_name,
    l.lecture_content AS lecture_content,
    l.start_enrolment_date AS start_enrolment_date,
    l.end_enrolment_date AS end_enrolment_date,
    l.start_study_date AS start_study_date,
    l.end_study_date AS end_study_date,
    l.lecture_img1 AS lecture_img1,
    l.lecture_img2 AS lecture_img2,
    l.lecture_vedio AS lecture_vedio,
    l.lecture_maxnum AS lecture_maxnum,
    l.lecture_minnum AS lecture_minnum,
    l.lecture_curnum AS lecture_curnum,
    l.lecture_act AS lecture_act,
    l.zoom_url AS zoom_url
FROM
    member m
        JOIN
    teacher t ON m.mno = t.mno
        JOIN
    lecture l ON t.teacher_no = l.lecture_no;