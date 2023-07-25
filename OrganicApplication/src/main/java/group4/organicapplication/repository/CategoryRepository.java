package group4.organicapplication.repository;

import group4.organicapplication.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
//    public long countByID(Integer categoryID);
}
