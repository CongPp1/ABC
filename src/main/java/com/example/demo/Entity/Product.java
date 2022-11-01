package com.example.demo.Entity;

import org.springframework.boot.convert.DataSizeUnit;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

//POJO: Plain Object Java Object

@Entity

public class Product {
    //This is primary key: ; // kieu sinh ra tu dong
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // tu dong sinh ra Id (Id++)
    private Long id;
    //Validate = constraint
    @Column(nullable = false, unique = true, length = 300)
    private String productName;
    private int year;
    private Double price;
    private String url;

    //Default constructor:

    public Product( String productName, int year, Double price, String url) {
        this.productName = productName;
        this.year = year;
        this.price = price;
        this.url = url;
    }

    public Product() {

    }
    //Calculated fieled = transient
    @Transient
    private int age; // age is calculated from year
    public int getAge(){
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return year == product.year && age == product.age && id.equals(product.id) && productName.equals(product.productName) && price.equals(product.price) && url.equals(product.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, year, price, url, age);
    }
}
