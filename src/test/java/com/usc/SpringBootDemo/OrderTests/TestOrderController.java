package com.usc.SpringBootDemo.OrderTests;

import com.usc.beans.Order;
import com.usc.beans.OrderProduct;
import com.usc.beans.Product;
import com.usc.beans.User;
import com.usc.beans.UserProfile;
import com.usc.controller.OrderController;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestOrderController {
  @InjectMocks
  private OrderController orderController;

  @Mock
  private OrderService orderService;

  private Order order;
  private List<Order> orderList;

  @BeforeEach
  public void setUp() {
    order = new Order();

    Product product = new Product("product", 1, 1, "image", "description");
    OrderProduct orderProduct = new OrderProduct(1, order, product);
    List<OrderProduct> purchases = Lists.newArrayList(orderProduct);
    User user = new User(2, "user", "user", Lists.newArrayList(new UserProfile(2)));

    order.setPurchases(purchases);
    order.setUser(user);
    orderList = Lists.newArrayList(order);
  }

  @AfterEach
  public void cleanUp() {
    order = null;
    orderList = null;
  }

  @Test
  public void testGetAllOrders() {
    when(orderService.getAllOrders()).thenReturn(orderList);

    List<Order> response = orderController.getOrders();
    verify(orderService, times(1)).getAllOrders();

    assertThat(response.size()).isEqualTo(1);
    assertThat(response.get(0).getUser().getUsername()).isEqualTo("user");
    assertThat(response.get(0).getPurchases().size()).isEqualTo(1);
    assertThat(response.get(0).getPurchases().get(0).getProduct().getName()).isEqualTo("product");
  }

  @Test
  public void testAddOrder() {
    when(orderService.addOrder(any(Order.class), any(Authentication.class)))
            .thenReturn(new Response(true));
    Authentication authentication = mock(Authentication.class);

    Response response = orderController.addOrder(order, authentication);
    verify(orderService, times(1)).addOrder(any(Order.class), any(Authentication.class));
    assertThat(response.getCode()).isEqualTo(200);
  }

  @Test
  public void testGetOrderById() {
    when(orderService.getOrder(order.getId())).thenReturn(order);

    Order response = orderController.getOrderById(0);
    verify(orderService, times(1)).getOrder(0);
    assertThat(response.getUser().getUsername()).isEqualTo("user");
    assertThat(response.getPurchases().size()).isEqualTo(1);
  }

  @Test
  public void testDeleteOrder() {
    when(orderService.deleteOrder(order.getId())).thenReturn(new Response(true));

    Response response = orderController.deleteOrder(0);
    verify(orderService, times(1)).deleteOrder(0);
    assertThat(response.getCode()).isEqualTo(200);
  }

  @Test
  public void testGetOrdersByUser() {
    when(orderService.getOrdersByUser(anyString(), any(Authentication.class)))
            .thenReturn(orderList);

    Authentication authentication = mock(Authentication.class);
    List<Order> response = orderController.getOrderByUser("user", authentication);
    verify(orderService, times(1)).getOrdersByUser("user", authentication);
    assertThat(response.size()).isEqualTo(1);
    assertThat(response.get(0).getUser().getUsername()).isEqualTo("user");
  }
}
