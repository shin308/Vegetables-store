package group4.organicapplication.service;

import group4.organicapplication.exception.CategoryNotFoundException;
import group4.organicapplication.model.Category;
import group4.organicapplication.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired private CategoryRepository repo;
    public List<Category> listAll(){
        return (List<Category>) repo.findAll();
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

    public void delete(Integer categoryID) throws CategoryNotFoundException {
//        long count = repo.countByID(categoryID);
//        if ( count == 0){
//            throw new CategoryNotFoundException("Could not find any category with ID: "+categoryID);
//        }
        repo.deleteById(categoryID);
    }

    public List<Category> findCategoryByName(String searchCategory) {
        return repo.findByCategoryNameContainingIgnoreCase(searchCategory);
    }
}
