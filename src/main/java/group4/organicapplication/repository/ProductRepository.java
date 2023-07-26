package group4.organicapplication.repository;

import group4.organicapplication.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
