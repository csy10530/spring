package com.usc.service;

import com.usc.beans.Product;
import com.usc.dao.ProductDao;
import com.usc.http.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductService {
  @Autowired
  ProductDao productDao;

  public List<Product> getProducts() {
    return productDao.findAll();
  }

  public Response addProduct(Product product) {
    productDao.save(product);
    return new Response(true, "New Product Added!");
  }

  public Response updateProduct(int id, Product product) {
    Product p = productDao.findProductById(id);
    if (p == null) {
      return new Response(false, "Product Not Found!");
    }

    p.setName(product.getName());
    p.setPrice(product.getPrice());
    p.setStock(product.getStock());
    p.setDescription(product.getDescription());
    p.setImage(product.getImage());

    productDao.save(p);
    return new Response(true, "Product Updated!");
  }

  public Response deleteProduct(int id) {
    Product p = productDao.findProductById(id);
    if (p == null) {
      return new Response(false, "Product Not Found!");
    }

    productDao.deleteProductById(id);
    return new Response(true, "Product Deleted!");
  }
}
