package group4.organicapplication;

import group4.organicapplication.model.Product;
import group4.organicapplication.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class SelectProductTest {
    @Autowired private ProductRepository repo;

    @Test
    public void testListAll(){
        Iterable<Product> products = repo.findAll();
        assertAll(() -> {assertThat(products).hasSizeGreaterThan(0);});
        for (Product product : products){
            System.out.println(product);
        }
    }
}
