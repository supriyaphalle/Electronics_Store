package com.bikkadIt.electronic.store.repositories;

import com.bikkadIt.electronic.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    List<Product> findByTitleContaining(String subTitle);

    List<Product> findByLiveTrue();
}
