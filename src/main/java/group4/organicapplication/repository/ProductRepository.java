package group4.organicapplication.repository;

import group4.organicapplication.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p JOIN ImportBill ib ON p.productID = ib.product.productID JOIN ImportProduct ip ON ib.importProduct.importID = ip.importID WHERE ip.supplier.supplierID = :supplierId")
    List<Product> findProductsBySupplierId(int supplierId);


    List<Product> findByProductNameContaining(String searchName);

    //mơi thêm
    @Query("SELECT p FROM Product p WHERE p.category.categoryID = :categoryID")
    List<Product> findByCategoryId(Integer categoryID);

    List<Product> findByProductNameContainingIgnoreCase(String searchProductName);
}
