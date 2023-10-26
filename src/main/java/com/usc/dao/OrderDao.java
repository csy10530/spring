package com.usc.dao;

import com.usc.beans.Order;
import com.usc.beans.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
  Order findOrderById(int id);
  List<Order> findOrdersByUser_Username(String username);
  void deleteOrderById(int id);
}
