package com.lab.inventory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity
@Table(name="products")
public class Product {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @NotBlank @Size(max=120) private String name;
  @NotBlank @Size(max=60) private String category;
  @Positive private double price;
  @Min(0) private int stock;
  public Product() {}
  public Product(String name,String category,double price,int stock){this.name=name;this.category=category;this.price=price;this.stock=stock;}
  public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
  public String getCategory(){return category;} public void setCategory(String v){category=v;}
  public double getPrice(){return price;} public void setPrice(double v){price=v;}
  public int getStock(){return stock;} public void setStock(int v){stock=v;}
}
