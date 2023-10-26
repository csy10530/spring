package com.usc.SpringBootDemo.ProductTests;

import com.usc.beans.Product;
import com.usc.dao.ProductDao;
import com.usc.http.Response;
import com.usc.service.ProductService;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestProductService {
  @InjectMocks
  private ProductService productService;

  @Mock
  private ProductDao productDao;

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
  public void testGetAllProductsService() {
    productDao.save(product);
    when(productDao.findAll()).thenReturn(productList);
    List<Product> response = productService.getProducts();
    assertThat(response.size()).isEqualTo(1);
    assertThat(response.get(0).getName()).isEqualTo("product");
    verify(productDao, times(1)).findAll();
  }

  @Test
  public void testAddProductService() {
    when(productDao.save(any(Product.class))).thenReturn(this.product);
    Response response = productService.addProduct(product);
    assertThat(response.getCode()).isEqualTo(200);
    verify(productDao, times(1)).save(product);
  }

  @Test
  public void testDeleteProductService() {
    when(productDao.findProductById(product.getId())).thenReturn(product);
    Response response = productService.deleteProduct(0);
    assertThat(response.getCode()).isEqualTo(200);
  }

  @Test
  public void testUpdateProductService() {
    Product newProduct = new Product("newProduct", 1, 1, "image", "description");

    when(productDao.findProductById(0)).thenReturn(newProduct);
    Response response = productService.updateProduct(0, newProduct);
    assertThat(response.getCode()).isEqualTo(200);
  }
}
