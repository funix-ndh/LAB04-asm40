package com.funix.lab04.asm40.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funix.lab04.asm40.model.Product;

public interface ProductRepositoryInterface extends JpaRepository<Product, Integer> {
    List<Product> findAllByOrderByCreatedAtDesc();
}
