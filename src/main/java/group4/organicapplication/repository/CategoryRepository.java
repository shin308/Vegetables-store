package group4.organicapplication.repository;

import group4.organicapplication.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findByCategoryNameContainingIgnoreCase(String searchCategory);
//    public long countByID(Integer categoryID);
}
