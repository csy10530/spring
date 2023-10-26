package com.usc.dao;

import com.usc.beans.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
  Product findProductById(int id);
  void deleteProductById(int id);
}
