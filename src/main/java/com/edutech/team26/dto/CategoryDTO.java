package com.edutech.team26.dto;

import com.edutech.team26.domain.Category;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CategoryDTO implements Serializable {
    private Long categoryId;
    private String categoryName;
    private Long parentId;
    private List<CategoryDTO> subCategories;

    int courseCount;

    public static List<CategoryDTO> of(List<Category> categories) {
        if (categories != null) {
            List<CategoryDTO> categoryDtoList = new ArrayList<>();
            for (Category x : categories) {
                categoryDtoList.add(of(x));
            }
            return categoryDtoList;
        }

        return null;
    }

    public static CategoryDTO of(Category category) {
        return CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }

}
