package group4.organicapplication.controller;

import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.Category;
import group4.organicapplication.model.User;
import group4.organicapplication.service.CategoryService;
import group4.organicapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes("loggedInUser")
public class CategoryController {
    @Autowired private CategoryService service;
    @Autowired
    private UserService userService;

    @GetMapping("/category")
    public String showCategoryList(@RequestParam(value = "searchCategory", required = false)String searchCategory,Model model){
        List<Category> categoryList;

        if (searchCategory != null && !searchCategory.isEmpty()) {
            categoryList = service.findCategoryByName(searchCategory);
        } else {
            // Nếu không có tên để tìm kiếm, hiển thị tất cả nhà cung cấp
            categoryList = service.listAll();
        }

//        model.addAttribute("category", categoryList);
        model.addAttribute("category",new Category());
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
    public String saveCategory(Category category){
        service.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category/edit/{categoryID}")
    public String showEditForm(@PathVariable("categoryID") Integer categoryID, Model model){
        try {
            Category category = service.get(categoryID);
            model.addAttribute("category",category);
            model.addAttribute("pageTitle","Cập nhật thông tin danh mục ID: "+categoryID);
            return "category_form";
        } catch (CategoryNotFoundException e) {
            return "redirect:/admin/category";
        }
    }

    @GetMapping("/category/delete/{categoryID}")
    public String DeleteCategory(@PathVariable("categoryID") Integer categoryID){
        try {
            service.delete(categoryID);
        } catch (CategoryNotFoundException e) {
            return "redirect:/admin/category";
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/category/search")
    public String SearchCategory(@RequestParam(value = "searchCategory", required = false)String searchCategory,Model model){
        List<Category> category;

        if (searchCategory != null && !searchCategory.isEmpty()) {
            category = service.findCategoryByName(searchCategory);
        } else {
            // Nếu không có tên để tìm kiếm, hiển thị tất cả nhà cung cấp
            category = service.listAll();
        }

        model.addAttribute("category", category);
        return "redirect:/admin/category";
    }

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }
    public User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loggedInUser");
    }
}
