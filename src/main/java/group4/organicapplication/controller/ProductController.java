package group4.organicapplication.controller;


import group4.organicapplication.model.Category;
import group4.organicapplication.model.Product;
import group4.organicapplication.model.Supplier;
import group4.organicapplication.model.User;
import group4.organicapplication.service.CategoryService;
import group4.organicapplication.service.ProductService;
import group4.organicapplication.service.SupplierService;
import group4.organicapplication.service.UserService;
import group4.organicapplication.util.fileUploadUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
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
    private CategoryService categoryService;

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

    @GetMapping()
    public String getProductList(@RequestParam(value = "supplierID", required = false) Integer supplierID,
                                 @RequestParam(value = "searchName", required = false) String searchName,
                                 Model model) {
        List<Product> products;


        if (searchName != null && !searchName.isEmpty()) {
            products = productService.findProductByProductName(searchName);
        } else if(supplierID !=null && supplierID !=0) { // lọc theo nhà cung cấp
            products = productService.getProductsBySupplierId(supplierID);
        }else {

            products = productService.getAllProduct();
        }
        model.addAttribute("products", products);
        List<Category> listCate = categoryService.listCategory();
        // Truyền danh sách nhà cung cấp vào model
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("categories",listCate);
        return "product";
    }

    @GetMapping("/delete")
    public String getProductListDeleted(@RequestParam(value = "supplierID", required = false) Integer supplierID,
                                 @RequestParam(value = "searchName", required = false) String searchName,
                                 Model model) {
        List<Product> products;


        if (searchName != null && !searchName.isEmpty()) {
            products = productService.findProductDeletedByProductName(searchName);
        } else if(supplierID !=null && supplierID !=0) { // lọc theo nhà cung cấp
            products = productService.getProductsDeletedBySupplierId(supplierID);
        }else {

            products = productService.getAllProductDeleted();
        }
        model.addAttribute("products", products);
        List<Category> listCate = categoryService.listCategory();
        // Truyền danh sách nhà cung cấp vào model
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("categories",listCate);
        return "productDeleted";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, RedirectAttributes ra,
                                @RequestParam("imageNew") MultipartFile multipartFile) {

        try{

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            product.setImg(fileName);
            Product productCreate = productService.createProduct(product);
            String uploadDir = "./src/main/resources/static/images/"+ productCreate.getProductID();
            fileUploadUtil.saveFile(uploadDir,fileName, multipartFile);
            if (productCreate != null) {
                ra.addFlashAttribute("message", "Thêm sản phẩm thành công!");
            }

            return "redirect:/admin/product";
        }catch (Exception e){

            e.getMessage();
            return "redirect:/admin/product";
        }
    }

    @GetMapping("/{productID}")
    @ResponseBody
    public ResponseEntity<Product> getProductById(@PathVariable int productID){
        Product product = productService.get(productID);
        if(product != null ) {
            return ResponseEntity.ok(product);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{productID}" , consumes = { "multipart/form-data" })
    public String updateSupplier(@PathVariable int productID, @RequestPart("product") @Valid Product product,
                                 @RequestParam(value= "image", required = false)  MultipartFile multipartFile) {
        if(multipartFile == null) {
            System.out.println(product + "ok");
            System.out.println(productID);
            productService.updateProduct(productID, product);
            return "redirect:/admin/product";
        }
        else {
            try{

                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                product.setImg(fileName);
                Product productEdit = productService.updateProduct(productID, product);
                String uploadDir = "./src/main/resources/static/images/"+ productEdit.getProductID();
                fileUploadUtil.saveFile(uploadDir,fileName, multipartFile);
                return "redirect:/admin/product";
            }catch (Exception e){

                e.getMessage();
                return "redirect:/admin/product";
            }
        }
    }

    @GetMapping("/restore/{productID}")
    public ResponseEntity<String> restoreProduct(@PathVariable int productID) {
        String message = productService.restoreProduct(productID);
        return new ResponseEntity<>(message, HttpStatus.OK) ;
    }
    @DeleteMapping("/{productID}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productID) throws IOException {
        String message = productService.softDeleteProduct(productID);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}