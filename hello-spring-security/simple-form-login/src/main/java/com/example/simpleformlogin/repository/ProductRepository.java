package com.example.simpleformlogin.repository;

import com.example.simpleformlogin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
