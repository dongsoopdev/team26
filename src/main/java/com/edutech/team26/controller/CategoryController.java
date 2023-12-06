package com.edutech.team26.controller;

import com.edutech.team26.biz.CategoryService;
import com.edutech.team26.dto.CategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/category/")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model) {

        List<CategoryDTO> list = categoryService.list();
        model.addAttribute("list", list);

        return "category/list";
    }

    @PostMapping("/add")
    public String add(Model model, CategoryDTO categoryDTO) {

        boolean result = categoryService.add(categoryDTO.getCategoryName());
        return "redirect:/category/list";
    }

    @PostMapping("/delete")
    public String del(Model model, CategoryDTO categoryDTO) {

        boolean result = categoryService.del(categoryDTO.getCategoryId());

        return "redirect:/category/list";
    }

    @PostMapping("/update")
    public String update(Model model, CategoryDTO categoryDTO) {

        boolean result = categoryService.update(categoryDTO);

        return "redirect:/category/list";
    }



    /*@GetMapping("/categoryList")
    public String boardList(Model model) {
        List<CategoryDTO> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "category/categoryList";

    }


    @RequestMapping("/category")
    public String getMainPage(Model model) {
        // category
        model.addAttribute("rootCategory", categoryService.createCategoryRoot());
        System.out.println(categoryService.createCategoryRoot());
        return "category";
    }
*/




}
