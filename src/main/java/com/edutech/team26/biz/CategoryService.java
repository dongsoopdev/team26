package com.edutech.team26.biz;

import com.edutech.team26.domain.Category;
import com.edutech.team26.dto.CategoryDTO;
import com.edutech.team26.mapper.CategoryMapper;
import com.edutech.team26.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;


    private final CategoryMapper categoryMapper;

    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }


    public List<CategoryDTO> list() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryDTO.of(categories);
    }


    public boolean add(String categoryName) {



        Category category = Category.builder()
                .categoryName(categoryName)
                .build();

        categoryRepository.save(category);

        return false;
    }


    public boolean update(CategoryDTO categoryDTO) {

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDTO.getCategoryId());

        if (optionalCategory.isPresent()) {

            Category category = optionalCategory.get();

            category.setCategoryName(categoryDTO.getCategoryName());
            categoryRepository.save(category);
        }

        return true;
    }


    public boolean del(long id) {
        categoryRepository.deleteById(id);
        return true;
    }


    public List<CategoryDTO> frontList(CategoryDTO categoryDto) {

        return categoryMapper.select(categoryDto);
    }


//    @Cacheable("categories")
//    public CategoryDTO createCategoryRoot() {
//        List<Category> c = categoryRepository.findAll();
//        System.out.println("카테고리: " + c);
//
//        Map<Long, List<CategoryDTO>> groupingByParent = categoryRepository.findAll()
//                .stream()
//                .map(ce -> new CategoryDTO(ce.getCategoryId(), ce.getCategoryName(), ce.getParentId()))
//                .collect(groupingBy(cd -> cd.getParentId()));

//        CategoryDTO rootCategoryDTO = null;
//
//        System.out.println("리스트: " + groupingByParent);
//        for (List<CategoryDTO> rootCategories : groupingByParent.values()) {
//            for (CategoryDTO categoryDTO : rootCategories) {
//                System.out.println("Parent ID: " + categoryDTO.getParentId());
//                System.out.println("Categories:");
//
//                System.out.println("  Category ID: " + categoryDTO.getCategoryId());
//                System.out.println("  Category Name: " + categoryDTO.getCategoryName());
//                System.out.println("  Parent ID: " + categoryDTO.getParentId());
//                System.out.println("---");
//
//                rootCategoryDTO = new CategoryDTO(categoryDTO.getCategoryId(), categoryDTO.getCategoryName(), categoryDTO.getParentId());
//
//                System.out.println("rootCategoryDTO" + rootCategoryDTO);
//                System.out.println("groupingByParent" +groupingByParent);
//                addSubCategories(rootCategoryDTO, groupingByParent);
//
//            }
//        }
//
//        CategoryDTO rootCategoryDto = new CategoryDTO(0l, "ROOT", null);
//        addSubCategories(rootCategoryDto, groupingByParent);
//
//
//      return rootCategoryDto;
//    }
//
//    private void addSubCategories(CategoryDTO parent, Map<Long, List<CategoryDTO>> groupingByParentId) {
//        System.out.println(parent);
//        // 1. parent의 키로 subCategories를 찾는다.
//        List<CategoryDTO> subCategories = groupingByParentId.get(parent.getCategoryId());
//
//        System.out.println(subCategories);
//
//        // 종료 조건
//        if (subCategories == null)
//            return;
//
//        // 2. sub categories 셋팅
//        parent.setSubCategories(subCategories);
//
//
//        // 3. 재귀적으로 subcategories들에 대해서도 수행
//        subCategories.stream()
//                .forEach(s -> {
//                    addSubCategories(s, groupingByParentId);
//                });
//    }
//
//
//
//    public List<CategoryDTO> findAll() {
//        List<Category> lst  = categoryRepository.findAll();
//        List<CategoryDTO> categoryList = lst.stream().map(category
//                -> modelMapper.map(category, CategoryDTO.class))
//                .collect(Collectors.toList());
//       return categoryList;
//    }

}
