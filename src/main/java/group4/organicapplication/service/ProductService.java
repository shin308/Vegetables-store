package group4.organicapplication.service;

import group4.organicapplication.model.ImportBill;
import group4.organicapplication.model.ImportProduct;
import group4.organicapplication.model.Product;
import group4.organicapplication.model.Supplier;
import group4.organicapplication.repository.ImportBillRepository;
import group4.organicapplication.repository.ImportProductRepository;
import group4.organicapplication.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImportBillRepository imBillRepository;
    @Autowired
    private ImportProductRepository imProductRepository;

    public List<Product> getProductsBySupplierId(int supplierId) {
        return productRepository.findProductsBySupplierId(supplierId);
    }
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public List<Product> findProductByProductName(String searchName) {
        return productRepository.findByProductNameContaining(searchName);
    }


    public Product get(Integer productID) {
        Optional<Product> productOptional = productRepository.findById(productID);
        return productOptional.get();
    }

    public List<Product> getProductsByCategoryId(Integer categoryID) {
        return productRepository.findByCategoryId(categoryID);
    }
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    public String deleteProduct(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()) {
            ImportBill importBills = imBillRepository.findByProductID(id);
            int importID = importBills.getImportProduct().getImportID();
            if(importBills != null) {
                imBillRepository.delete(importBills);
            }
            if(importBills != null) {
                ImportProduct importProduct = imProductRepository.findByImportID(importID);
                System.out.println(importProduct + "ok");

                imProductRepository.delete(importProduct);
            }

            productRepository.deleteById(id);

            return "Xóa sản phẩm thành công";

        }
        else {
            return "Không tìm thấy sản phẩm nào với id = " + id;
        }


    }
    public Product updateProduct(int productID, Product product) {
        Product productExist = productRepository.findById(productID).orElse(null);
        if(productExist != null) {
            productExist.setProductName(product.getProductName());
            productExist.setDescription(product.getDescription());
            productExist.setPrice(product.getPrice());
            productExist.setImg(product.getImg());
            productExist.setUnit(product.getUnit());
            productExist.setQuantity(product.getQuantity());
            productExist.setCategory(product.getCategory());
            return productRepository.save(productExist);
        }
        return null;
    }
}
