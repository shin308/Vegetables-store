package group4.organicapplication.controller;

import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.Category;
import group4.organicapplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired private CategoryService service;

    @GetMapping("/category")
    public String showCategoryList(Model model){
        List<Category> categoryList = service.listAll();
        model.addAttribute("categoryList", categoryList);
        return "category";
    }

    @GetMapping("/category/new")
    public String showNewForm(Model model){
        model.addAttribute("category",new Category());
        model.addAttribute("pageTitle","Add New Category");
        return "category_form";
    }

    @PostMapping("/category/save")
    public String saveCategory(Category category, RedirectAttributes ra){
        service.save(category);
        ra.addFlashAttribute("message","The category has been saved successfully.");
        return "redirect:/category";
    }

    @GetMapping("/category/edit/{categoryID}")
    public String showEditForm(@PathVariable("categoryID") Integer categoryID, Model model, RedirectAttributes ra){
        try {
            Category category = service.get(categoryID);
            model.addAttribute("category",category);
            model.addAttribute("pageTitle","Edit Category (ID: "+categoryID+")");
            return "category_form";
        } catch (CategoryNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/category";
        }
    }

    @GetMapping("/category/delete/{categoryID}")
    public String DeleteCategory(@PathVariable("categoryID") Integer categoryID, RedirectAttributes ra){
        try {
            service.delete(categoryID);
            ra.addFlashAttribute("message", "The Category ID " + categoryID + " has been deleted.");
        } catch (CategoryNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/category";
    }
}
