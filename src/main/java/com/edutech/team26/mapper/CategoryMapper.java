package com.edutech.team26.mapper;

import com.edutech.team26.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

	List<CategoryDTO> select(CategoryDTO categoryDto);
}
