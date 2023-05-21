package com.ecom.project.ubunfakn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.project.ubunfakn.entities.Product;

public interface ProductDao extends JpaRepository<Product,Integer> {
    
    @Query("select p from Product p where p.category=:cat")
    public List<Product> findByCategory(@Param("cat") String category);

    @Query("select distinct p.category from Product p")
    public List<String> getAllCategory();

    @Query("select distinct p.brand from Product p")
    public List<String> getAllBrand();

    @Query("select p.name from Product p")
    public List<String> getAllNames();

    @Query("select p from Product p where p.price<=8000")
    public List<Product> budget();

    @Query("select p from Product p where id=:i")
    public Product getByProductId(@Param("i")int id);

    // @Query("select p from Product p where Categories_Id=:n")
    // public List<Product> getById(@Param("n") int id);

    public List<Product> findByCategories_Id(@Param("n") int id);

    @Query("select p from Product p where p.discount<=:p")
    public List<Product> getAllByDiscount(@Param("p")int p);

    @Query("select p from Product p where p.discount>=:p")
    public List<Product> getAllByExactDiscount(@Param("p")int p);

    public List<Product> findByNameContaining(String keyWord);

}
