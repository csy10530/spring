package com.usc.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "usc_order")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "ORDER_SEQ")
  @SequenceGenerator(name = "ORDER_SEQ", sequenceName = "USC_ORDER_SEQ", allocationSize = 1)
  private int id;

  @Column(name = "purchase_date")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date purchaseDate;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderProduct> purchases;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnoreProperties({"password", "enabled", "credentialsNonExpired", "authorities", "accountNonExpired", "accountNonLocked", "hibernateLazyInitializer"})
  private User user;

  public Order() {
  }

  public Order(Date purchaseDate, List<OrderProduct> purchases) {
    this.purchaseDate = purchaseDate;
    this.purchases = purchases;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public List<OrderProduct> getPurchases() {
    return purchases;
  }

  public void setPurchases(List<OrderProduct> purchases) {
    this.purchases = purchases;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", purchaseDate=" + purchaseDate +
            ", purchases=" + purchases +
            ", user=" + user +
            '}';
  }
}
