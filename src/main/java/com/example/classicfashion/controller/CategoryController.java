package com.example.classicfashion.controller;

import com.example.classicfashion.model.Category;
import com.example.classicfashion.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showCategoryList(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(required = false) String keyword) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Category> categoryPage;
        if(keyword != null && !keyword.isEmpty()){
            categoryPage = categoryService.searchCategories(keyword, pageable);
            model.addAttribute("keyword", keyword);

            if(categoryPage.isEmpty()){
               model.addAttribute("message", "Không có danh mục nào với từ khóa: " + keyword);
            }
        } else {
            categoryPage = categoryService.getAllCategory(pageable);
        }
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        return "admin/category-list";
    }

    @GetMapping("/new")
    public String showCreateCategoryForm(Model model){
        model.addAttribute("category", new Category());
        return "admin/create_category_form";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category, Model model, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "admin/create_category_form";
        }
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model){
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "admin/create_category_form";
    }
}
