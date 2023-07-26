package group4.organicapplication.service;

import group4.organicapplication.model.Product;
import group4.organicapplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectProductService {
    @Autowired private ProductRepository repo;

    public List<Product> selectAll(){
        return (List<Product>) repo.findAll();
    }
}
