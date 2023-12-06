package com.edutech.team26.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureParam extends CommonParam {

	long lecture_no; // lecture.id
	long categoryId;
}
