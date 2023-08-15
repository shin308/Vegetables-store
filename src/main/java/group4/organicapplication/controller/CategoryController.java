package group4.organicapplication.controller;

import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.Category;
import group4.organicapplication.model.User;
import group4.organicapplication.service.CategoryService;
import group4.organicapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            // Nếu không có tên để tìm kiếm, hiển thị tất cả Danh Mục
            categoryList = service.listCategory();
        }
//        model.addAttribute("category", categoryList);
        model.addAttribute("category",new Category());
        model.addAttribute("categoryList", categoryList);
        return "category";
    }

    @GetMapping("/category/garbage")
    public String showGarbageCategoryList(Model model){
        List<Category> categoryList;
        categoryList = service.listCategoryGarbage();

        model.addAttribute("categoryList", categoryList);
        return "category_garbage";
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

    @DeleteMapping("/category/softdelete/{categoryID}")
    public ResponseEntity<String> SoftDeleteCategory(@PathVariable("categoryID") Integer categoryID){
        String message = service.softdelete(categoryID);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/category/delete/{categoryID}")
    public ResponseEntity<String> DeleteCategory(@PathVariable("categoryID") Integer categoryID){

        String message = service.delete(categoryID);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/category/restore/{categoryID}")
    public ResponseEntity<String> RestoreCategory(@PathVariable("categoryID") Integer categoryID){
        String message = service.restore(categoryID);
        return new ResponseEntity<>(message, HttpStatus.OK);
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
