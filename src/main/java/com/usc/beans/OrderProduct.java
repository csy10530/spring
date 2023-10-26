package com.usc.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "usc_order_product")
public class OrderProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "ORDER_PRODUCT_SEQ")
  @SequenceGenerator(name = "ORDER_PRODUCT_SEQ", sequenceName = "USC_ORDER_PRODUCT_SEQ", allocationSize = 1)
  private int id;

  @Column(name = "qty", nullable = false)
  @Positive
  private int qty;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "order_id")
  @JsonIgnoreProperties("purchases")
  private Order order;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  private Product product;

  public OrderProduct() {
  }

  public OrderProduct(int qty, Order order, Product product) {
    this.qty = qty;
    this.order = order;
    this.product = product;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @Override
  public String toString() {
    return "OrderProduct{" +
            "id=" + id +
            ", qty=" + qty +
            ", order=" + order +
            ", product=" + product +
            '}';
  }
}
