package com.edutech.team26.mapper;

import com.edutech.team26.dto.LectureDTO;

import com.edutech.team26.model.LectureParam;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LectureMapper {
	long selectListCount(LectureParam lectureParam);
	List<LectureDTO> selectList(LectureParam lectureParam);
}
