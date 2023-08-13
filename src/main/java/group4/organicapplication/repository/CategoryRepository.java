package group4.organicapplication.repository;

import group4.organicapplication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByCategoryNameContainingIgnoreCase(String searchCategory);
//    public long countByID(Integer categoryID);
    @Query("SELECT c FROM Category c WHERE c.deleted = false")
    List<Category> findCategory();

    @Query("SELECT c FROM Category c WHERE c.deleted = true")
    List<Category> findCategoryGarbage();
}
