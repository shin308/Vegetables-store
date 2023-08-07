package group4.organicapplication.controller;


import group4.organicapplication.model.Product;
import group4.organicapplication.model.Supplier;
import group4.organicapplication.model.User;
import group4.organicapplication.service.ProductService;
import group4.organicapplication.service.SupplierService;
import group4.organicapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
@SessionAttributes("loggedInUser")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService userService;


//    //lấy danh sách sản phẩm theo nhà cung cấp
//    @GetMapping("/bySupplier/{supplierID}")
//    public ResponseEntity<List<Product>> getProductsBySupplierId(@PathVariable int supplierID, Model model) {
//        List<Product> products = productService.getProductsBySupplierId(supplierID);
//        ProductDto productDto = new ProductDto();
//        if(products.isEmpty()) {
//
//            productDto.setProducts(new ArrayList<>());
//            model.addAttribute("products", productDto);
//            return ResponseEntity.ok(new ArrayList<>());
//        }else {
//            productDto.setProducts(products);
//            model.addAttribute("products", productDto);
//            return ResponseEntity.ok(products);
//
//        }
//    }

    @GetMapping
    public String getProductList(@RequestParam(value = "supplierID", required = false) Integer supplierID,
                                 @RequestParam(value = "searchName", required = false) String searchName,
                                 Model model) {
        List<Product> products;

        // Nếu người dùng nhập tên nhà cung cấp để tìm kiếm, thì lọc dữ liệu dựa trên tên
        if (searchName != null && !searchName.isEmpty()) {
            products = productService.findProductByProductName(searchName);
        } else if(supplierID !=null && supplierID !=0) { // lọc theo nhà cung cấp
            products = productService.getProductsBySupplierId(supplierID);
        }else {
            // Nếu không hiển thị tất cả sản phẩm
            products = productService.getAllProduct();
        }
        model.addAttribute("products", products);

        // Truyền danh sách nhà cung cấp vào model
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "product";
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
