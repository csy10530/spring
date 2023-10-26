package com.usc.controller;

import com.usc.beans.Order;
import com.usc.beans.User;
import com.usc.http.Response;
import com.usc.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
  @Autowired
  OrderService orderService;

  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
  @PostMapping
  public Response addOrder(@RequestBody Order order, Authentication authentication) {

    return orderService.addOrder(order, authentication);
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping("/all")
  public List<Order> getOrders() {
    return orderService.getAllOrders();
  }

  @GetMapping("/{id}")
  public Order getOrderById(@PathVariable int id) {
    return orderService.getOrder(id);
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
  @GetMapping("/user/{username}")
  public List<Order> getOrderByUser(@PathVariable String username, Authentication authentication) {
    return orderService.getOrdersByUser(username, authentication);
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public Response deleteOrder(@PathVariable int id) {
    return orderService.deleteOrder(id);
  }
}
