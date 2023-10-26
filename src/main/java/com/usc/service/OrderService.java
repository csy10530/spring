package com.usc.service;

import com.usc.beans.Order;
import com.usc.beans.OrderProduct;
import com.usc.beans.Product;
import com.usc.beans.User;
import com.usc.dao.OrderDao;
import com.usc.dao.ProductDao;
import com.usc.dao.UserDao;
import com.usc.http.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {
  @Autowired
  OrderDao orderDao;

  @Autowired
  ProductDao productDao;

  @Autowired
  UserDao userDao;

  public List<Order> getAllOrders() {
    return orderDao.findAll();
  }

  public Response addOrder(Order order, Authentication authentication) {
    order.setPurchaseDate(new Date());
    List<OrderProduct> purchases = order.getPurchases();
    //System.out.println(purchases);

    for (OrderProduct p : purchases) {
      Product product = p.getProduct();
      Product targetProduct = productDao.findProductById(product.getId());
      if (targetProduct == null) {
        return new Response(false, "Product Not Found!");
      }

      p.setProduct(targetProduct);
      p.setOrder(order);
    }
    order.setUser(userDao.findByUsername(authentication.getName()));
    order.setPurchases(purchases);

    orderDao.save(order);
    return new Response(true, "Order Placed!");
  }

  public Order getOrder(int id) {
    return orderDao.findOrderById(id);
  }

  public List<Order> getOrdersByUser(String username, Authentication authentication) {

    if (username.equals(authentication.getName()) || isAdmin(authentication.getAuthorities())) {

      return orderDao.findOrdersByUser_Username(username);
    }
    return new ArrayList<>();
  }

  private boolean isAdmin(Collection<? extends GrantedAuthority> profiles) {
    boolean isAdmin = false;
    for (GrantedAuthority profile : profiles) {
      if (profile.getAuthority().equals("ROLE_ADMIN")) {
        isAdmin = true;
      }
    }

    return isAdmin;
  }

  public Response deleteOrder(int id) {
    Order o = orderDao.findOrderById(id);
    if (o == null) {
      return new Response(false, "Order Not Found!");
    }

    orderDao.deleteOrderById(id);
    return new Response(true, "Order Deleted!");
  }
}
