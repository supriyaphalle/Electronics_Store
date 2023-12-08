package com.bikkadIt.electronic.store.repositories;

import com.bikkadIt.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    Page<Product> findByNameContaining(String productName, Pageable pageable );

    Page<Product> findByLiveTrue(Pageable pageable );
}
