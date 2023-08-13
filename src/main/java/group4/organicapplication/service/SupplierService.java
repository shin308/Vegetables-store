package group4.organicapplication.service;




import group4.organicapplication.model.ImportBill;
import group4.organicapplication.model.ImportProduct;
import group4.organicapplication.model.Supplier;
import group4.organicapplication.repository.ImportBillRepository;
import group4.organicapplication.repository.ImportProductRepository;
import group4.organicapplication.repository.ProductRepository;
import group4.organicapplication.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ImportProductRepository importProductRepository;
    @Autowired
    private ImportBillRepository importBillRepository;
    @Autowired
    private ProductRepository productRepository;

    // Phương thức tạo mới nhà cung cấp
    @Transactional
    public Supplier createSupplier(Supplier supplier) {
        Supplier existSupplier = supplierRepository.findByPhone(supplier.getPhone());
        if(existSupplier != null) {
            return null;
        } else {
            return supplierRepository.save(supplier);
        }
    }

    // Phương thức lấy thông tin nhà cung cấp theo ID
    public Supplier getSupplierById(int supplierID) {
        // Logic để lấy thông tin nhà cung cấp theo ID
        return supplierRepository.findById(supplierID).orElse(null);
    }

    // Phương thức cập nhật thông tin nhà cung cấp
    public Supplier updateSupplier(int supplierID, Supplier supplier) {
        // Logic để cập nhật thông tin nhà cung cấp
        Supplier existingSupplier = supplierRepository.findById(supplierID).orElse(null);
        if (existingSupplier != null) {
            existingSupplier.setSupplierName(supplier.getSupplierName());
            existingSupplier.setAddress(supplier.getAddress());
            existingSupplier.setPhone(supplier.getPhone());
            // Cập nhật các thuộc tính khác
            return supplierRepository.save(existingSupplier);
        }
        return null;
    }

    // Phương thức xóa nhà cung cấp
    public String deleteSupplier(int supplierID) {
        // Xóa các bản ghi liên kết trong bảng ImportBill
        List<ImportBill> importBills = importBillRepository.findBySupplierID(supplierID);
        for (ImportBill importBill : importBills) {
            importBillRepository.delete(importBill);
        }

        // Xóa các bản ghi liên kết trong bảng ImportProduct
        List<ImportProduct> importProducts = importProductRepository.findBySupplierID(supplierID);
        for (ImportProduct importProduct : importProducts) {
            importProductRepository.delete(importProduct);
        }

        // Kiểm tra xem có tồn tại nhà cung cấp với ID tương ứng hay không
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierID);
        if (supplierOptional.isPresent()) {
            supplierRepository.deleteById(supplierID);
            return "Xóa nhà cung cấp thành công!";
        } else {
            return "Không tìm thấy nhà cung cấp để xóa!";
        }
    }

    // Phương thức lấy danh sách tất cả nhà cung cấp
    public List<Supplier> getAllSuppliers() {
        // Logic để lấy danh sách tất cả nhà cung cấp
        return supplierRepository.findSuppliers();
    }

    public List<Supplier> findSuppliersByName(String searchName) {
        return supplierRepository.findBySupplierNameContainingIgnoreCase(searchName);
    }

    public String softdeleteSupplier(int supplierID) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierID);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplier.setDeleted(true);
            supplierRepository.save(supplier);
            return "Xóa nhà cung cấp thành công!";
        } else {
            return "Không tìm thấy nhà cung cấp để xóa!";
        }
    }

    public List<Supplier> getGarbageSuppliers() {
        return supplierRepository.findGarbageSuppliers();
    }

    public String restore(Integer supplierID) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierID);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplier.setDeleted(false);
            supplierRepository.save(supplier);
            return "Khôi phục nhà cung cấp thành công!";
        } else {
            return "Không tìm thấy nhà cung cấp để xóa!";
        }
    }
}
