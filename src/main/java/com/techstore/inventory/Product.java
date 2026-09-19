package com.techstore.inventory;

import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Product {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private String name;
  @Column(nullable=false) private String category;
  @Column(nullable=false) private double price;
  @Column(nullable=false) private int stock;
  public Product() {}
  public Product(String name,String category,double price,int stock){this.name=name;this.category=category;this.price=price;this.stock=stock;}
  public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
  public String getCategory(){return category;} public void setCategory(String v){category=v;}
  public double getPrice(){return price;} public void setPrice(double v){price=v;}
  public int getStock(){return stock;} public void setStock(int v){stock=v;}
}
