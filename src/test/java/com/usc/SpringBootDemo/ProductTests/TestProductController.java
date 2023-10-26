package com.usc.SpringBootDemo.ProductTests;

import com.usc.beans.Product;
import com.usc.controller.ProductController;
import com.usc.http.Response;
import com.usc.service.ProductService;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestProductController {
  @InjectMocks
  private ProductController productController;

  @Mock
  private ProductService productService;

  private Product product;
  private List<Product> productList;

  @BeforeEach
  public void setUp() {
    product = new Product("product", 1.0, 1, "image", "description");
    productList = Lists.newArrayList(product);
  }

  @AfterEach
  public void cleanUp() {
    product = null;
    productList = null;
  }

  @Test
  public void testGetAllProducts() {
    when(productService.getProducts()).thenReturn(this.productList);

    List<Product> response = productController.getProducts();
    assertThat(response.size()).isEqualTo(1);
    assertThat(response.get(0).getName()).isEqualTo("product");
  }

  @Test
  public void testAddProduct() {
    when(productService.addProduct(any(Product.class))).thenReturn(new Response(true));
    Response response = productController.addProduct(product);
    assertThat(response.getCode()).isEqualTo(200);
  }

  @Test
  public void testDeleteProduct() {
    when(productService.deleteProduct(product.getId())).thenReturn(new Response(true));
    Response response = productController.deleteProduct(0);
    verify(productService, times(1)).deleteProduct(0);
    assertThat(response.getCode()).isEqualTo(200);
  }

  @Test
  public void testUpdateProduct() {
    when(productService.updateProduct(anyInt(), any(Product.class))).thenReturn(new Response(true));

    Product newProduct = new Product("newProduct", 1, 1, "image", "description");
    Response response = productController.updateProduct(0, newProduct);

    verify(productService, times(1)).updateProduct(0, newProduct);
    assertThat(response.getCode()).isEqualTo(200);
  }
}
