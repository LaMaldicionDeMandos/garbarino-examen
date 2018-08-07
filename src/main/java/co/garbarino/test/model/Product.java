package co.garbarino.test.model;

import java.util.List;

/**
 * Created by boot on 07/06/2018.
 */
public class Product {
    private final String id;
    private final String name;
    private final String description;
    private final int price;
    private final int listPrice;
    private final int stock;
    private List<Review> reviews;

    Product() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.price = 0;
        this.listPrice = 0;
        this.stock = 0;
        this.reviews = null;
    }

    public Product(String id, String name, String description, int price, int listPrice, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.listPrice = listPrice;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getListPrice() {
        return listPrice;
    }

    public int getStock() {
        return stock;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id != null ? id.equals(product.id) : product.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
