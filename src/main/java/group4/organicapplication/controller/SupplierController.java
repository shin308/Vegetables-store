package group4.organicapplication.controller;

;
import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.Category;
import group4.organicapplication.model.Supplier;
import group4.organicapplication.model.User;
import group4.organicapplication.service.SupplierService;
import group4.organicapplication.service.UserService;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/admin/suppliers")
@SessionAttributes("loggedInUser")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService userService;

    //createNewSupplier
    @PostMapping
    public String createSupplier(@ModelAttribute Supplier supplier, Model model) {
        Supplier createdSupplier = supplierService.createSupplier(supplier);
        if (createdSupplier != null) {
            model.addAttribute("message", "Thêm nhà cung cấp thành công!");
        } else {
            model.addAttribute("error", "Số điện thoại đã tồn tại. Vui lòng kiểm tra lại!");
        }
        return "redirect:/admin/suppliers";
    }

    //get supplier by id
    @GetMapping("/{supplierID}")
    @ResponseBody
    public ResponseEntity<Supplier> getSupplierById(@PathVariable int supplierID) {
        Supplier supplier = supplierService.getSupplierById(supplierID);
        if (supplier != null) {
            return ResponseEntity.ok(supplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //update supllier by id
    // Phương thức xử lý cập nhật thông tin nhà cung cấp
    @PutMapping("/{supplierID}")
    public String updateSupplier(@PathVariable int supplierID,@RequestBody Supplier supplier) {
        supplierService.updateSupplier(supplierID, supplier);
        return "redirect:/admin/suppliers";

    }

    //delete supplier by id
    @DeleteMapping("/{supplierID}")
    public ResponseEntity<String> deleteSupplier(@PathVariable int supplierID) {
        String message = supplierService.deleteSupplier(supplierID);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/softdelete/{supplierID}")
    public ResponseEntity<String> softdeleteSupplier(@PathVariable int supplierID) {
        String message = supplierService.softdeleteSupplier(supplierID);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/restore/{supplierID}")
    public ResponseEntity<String> RestoreSupplier(@PathVariable int supplierID) {
        String message = supplierService.restore(supplierID);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    //get all supplier
    @GetMapping
    public String getSupplierList(@RequestParam(value = "searchName", required = false) String searchName, Model model) {
        List<Supplier> suppliers;

        // Nếu người dùng nhập tên nhà cung cấp để tìm kiếm, thì lọc dữ liệu dựa trên tên
        if (searchName != null && !searchName.isEmpty()) {
            suppliers = supplierService.findSuppliersByName(searchName);
        } else {
            // Nếu không có tên để tìm kiếm, hiển thị tất cả nhà cung cấp
            suppliers = supplierService.getAllSuppliers();
        }

        model.addAttribute("suppliers", suppliers);
        return "supplier"; // Tên của file supplier.html trong thư mục templates
    }

    @GetMapping("/garbage")
    public String showGarbageSupplierList(Model model){
        List<Supplier> suppliers = supplierService.getGarbageSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "supplier_garbage";
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
