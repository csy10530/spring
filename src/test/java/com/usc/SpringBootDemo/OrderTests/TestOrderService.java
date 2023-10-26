package com.usc.SpringBootDemo.OrderTests;

import com.usc.beans.Order;
import com.usc.beans.OrderProduct;
import com.usc.beans.Product;
import com.usc.beans.User;
import com.usc.beans.UserProfile;
import com.usc.dao.OrderDao;
import com.usc.dao.ProductDao;
import com.usc.dao.UserDao;
import com.usc.http.Response;
import com.usc.service.OrderService;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestOrderService {
  @InjectMocks
  private OrderService orderService;

  @Mock
  private OrderDao orderDao;

  @Mock
  private ProductDao productDao;
  @Mock
  private UserDao userDao;

  private Product product;
  private Order order;
  private User user;
  private List<Order> orderList;

  @BeforeEach
  public void setUp() {
    order = new Order();
    product = new Product("product", 1, 1, "image", "description");
    user = new User(2, "user", "user", Lists.newArrayList(new UserProfile(2)));

    OrderProduct orderProduct = new OrderProduct(1, order, product);
    List<OrderProduct> purchases = Lists.newArrayList(orderProduct);

    order.setPurchases(purchases);
    order.setUser(user);
    orderList = Lists.newArrayList(order);
  }

  @AfterEach
  public void cleanUp() {
    order = null;
    orderList = null;
    product = null;
    user = null;
  }

  @Test
  public void testGetAllOrdersService() {
    orderDao.save(order);
    when(orderDao.findAll()).thenReturn(orderList);

    List<Order> response = orderService.getAllOrders();
    verify(orderDao, times(1)).findAll();
    assertThat(response.size()).isEqualTo(1);
    assertThat(response.get(0).getUser().getUsername()).isEqualTo("user");
    assertThat(response.get(0).getPurchases().get(0).getProduct().getName()).isEqualTo("product");
  }

  @Test
  public void testAddOrderService() {
    when(orderDao.save(any(Order.class))).thenReturn(order);
    when(productDao.findProductById(anyInt())).thenReturn(product);
    when(userDao.findByUsername(any())).thenReturn(user);

    Authentication authentication = mock(Authentication.class);
    Response response = orderService.addOrder(order, authentication);

    verify(orderDao, times(1)).save(order);
    verify(userDao, times(1)).findByUsername(any());
    verify(productDao, times(1)).findProductById(0);
    assertThat(response.getCode()).isEqualTo(200);
  }

  @Test
  public void testGetOrderService() {
    when(orderDao.findOrderById(order.getId())).thenReturn(order);

    Order response = orderService.getOrder(0);
    verify(orderDao, times(1)).findOrderById(0);
    assertThat(response.getUser().getUsername()).isEqualTo("user");
    assertThat(response.getPurchases().size()).isEqualTo(1);
  }

  @Test
  public void testDeleteOrderService() {
    when(orderDao.findOrderById(order.getId())).thenReturn(order);
    Response response = orderService.deleteOrder(order.getId());
    verify(orderDao, times(1)).deleteOrderById(0);
    assertThat(response.getCode()).isEqualTo(200);
  }
}
