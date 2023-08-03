package group4.organicapplication.service;

import group4.organicapplication.model.Product;
import group4.organicapplication.model.Supplier;
import group4.organicapplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

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
}
