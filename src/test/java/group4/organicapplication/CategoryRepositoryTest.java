package group4.organicapplication;

import group4.organicapplication.model.Category;
import group4.organicapplication.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {
    @Autowired private CategoryRepository repo;

    @Test
    public void testAddNew(){
        Category category = new Category();
        category.setCategoryName("Vegetables");

        Category saveCategory = repo.save(category);
        assertAll(
                () -> {
                    assertThat(saveCategory).isNotNull();
                    assertThat(saveCategory.getCategoryID()).isGreaterThan(0);
                }
        );
    }

    @Test
    public void testListAll(){
        Iterable<Category> categories = repo.findAll();
        assertAll(() -> {assertThat(categories).hasSizeGreaterThan(0);});
        for (Category category : categories){
            System.out.println(category);
        }
    }

    @Test
    public void testUpdate(){
        Integer categoryId = 1;
        Optional<Category> optionalCategory = repo.findById(categoryId);
        Category category = optionalCategory.get();
        category.setCategoryName("Fruits");
        repo.save(category);

        Category updateCategory = repo.findById(categoryId).get();
        assertAll(() -> {assertThat(updateCategory.getCategoryName()).isEqualTo("Fruits");});
    }

    @Test
    public void testGet(){
        Integer categoryId = 2;
        Optional<Category> optionalCategory = repo.findById(categoryId);
        assertAll(() -> {assertThat(optionalCategory).isPresent();});
        System.out.println(optionalCategory.get());
    }

    @Test
    public void testDelete(){
        Integer categoryId = 2;
        repo.deleteById(categoryId);

        Optional<Category> optionalCategory = repo.findById(categoryId);
        assertAll(() -> {assertThat(optionalCategory).isNotPresent();});
    }
}
