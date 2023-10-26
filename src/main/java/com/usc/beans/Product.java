package com.usc.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "usc_product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "PRODUCT_SEQ")
  @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "USC_PRODUCT_SEQ", allocationSize = 1)
  private int id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "price", nullable = false)
  @Positive
  private double price;

  @Column(name = "stock", nullable = false)
  @PositiveOrZero
  private int stock;

  @Column(name = "image")
  private String image;

  @Column(name = "description")
  private String description;

  public Product() {
  }

  public Product(String name, double price, int stock, String image) {
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  public Product(String name, double price, int stock, String image, String description) {
    this.name = name;
    this.price = price;
    this.stock = stock;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", stock=" + stock +
            ", description='" + description + '\'' +
            '}';
  }
}
