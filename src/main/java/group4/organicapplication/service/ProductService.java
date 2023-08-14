package group4.organicapplication.service;

import group4.organicapplication.model.*;
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

    public Product getProductById(Integer productId) {
        return productRepository.findByProductID(productId);
    }
    public List<Product> getProductsBySupplierId(int supplierId) {
        return productRepository.findProductsBySupplierId(supplierId);
    }
    public List<Product> getProductsDeletedBySupplierId(int supplierId) {
        return productRepository.findProductsDeleteBySupplierId(supplierId);
    }
    public List<Product> getAllProduct() {
        return productRepository.findProduct();
    }

    public List<Product> getAllProductDeleted() {
        return productRepository.findProductDeleted();
    }
    public List<Product> findProductByProductName(String searchName) {
        return productRepository.findByProductName(searchName);
    }
    public List<Product> findProductDeletedByProductName(String searchName) {
        return productRepository.findByProductNameDeleted(searchName);
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

    public String softDeleteProduct(Integer productID)  {
        Optional<Product> optionalProduct = productRepository.findById(productID);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setDeleted(true);
            productRepository.save(product);
            return "Xóa sản phẩm thành công";
        }
        else {
            return "Không tìm thấy sản phẩm với ID:" + productID;
        }
    }

    public String restoreProduct(Integer productID)  {
        Optional<Product> optionalProduct = productRepository.findById(productID);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setDeleted(false);
            productRepository.save(product);
            return "Khôi phục sản phẩm thành công";
        }
        else {
            return "Không tìm thấy sản phẩm với ID:" + productID;
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
