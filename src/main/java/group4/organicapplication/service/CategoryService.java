package group4.organicapplication.service;

import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.Category;
import group4.organicapplication.model.Product;
import group4.organicapplication.repository.CategoryRepository;
import group4.organicapplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired private CategoryRepository repo;

    @Autowired private ProductRepository productRepository;
//    public List<Category> listAll(){
//        return (List<Category>) repo.findAll();
//    }

    public List<Category> listCategory(){
        return (List<Category>) repo.findCategory();
    }

    public void save(Category category) {
        repo.save(category);
    }

    public Category get(Integer categoryID) throws CategoryNotFoundException{
        Optional<Category> result = repo.findById(categoryID);
        if(result.isPresent()){
            return result.get();
        }
        throw  new CategoryNotFoundException("Could not find any category with ID: " + categoryID);
    }

    public void softdelete(Integer categoryID) throws CategoryNotFoundException {
        List<Product>products = productRepository.findByCategoryId(categoryID);
//        productRepository.deleteAll(products);
        for (Product product : products) {
            product.setDeleted(true);
            productRepository.save(product);
        }

        Optional<Category> optionalCategory = repo.findById(categoryID);
        Category category = optionalCategory.get();
        category.setDeleted(true);
        repo.save(category);
    }

    public void delete(Integer categoryID) throws CategoryNotFoundException {
        List<Product>products = productRepository.findByCategoryId(categoryID);
        productRepository.deleteAll(products);

        repo.deleteById(categoryID);
    }

    public void restore(Integer categoryID) throws CategoryNotFoundException {
        List<Product>products = productRepository.findByCategoryId(categoryID);
//        productRepository.deleteAll(products);
        for (Product product : products) {
            product.setDeleted(false);
            productRepository.save(product);
        }

        Optional<Category> optionalCategory = repo.findById(categoryID);
        Category category = optionalCategory.get();
        category.setDeleted(false);
        repo.save(category);
    }

    public List<Category> findCategoryByName(String searchCategory) {
        return repo.findByCategoryNameContainingIgnoreCase(searchCategory);
    }

    public List<Category> listCategoryGarbage() {
        return (List<Category>) repo.findCategoryGarbage();
    }
}
