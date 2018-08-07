package co.garbarino.test.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by boot on 07/06/2018.
 */
@Entity
public class LegacyProduct {
    @Id
    private final String id;
    private final String name;
    private final String description;
    private final int price;
    private final int listPrice;
    private final int stock;
    private final boolean used;

    LegacyProduct() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.price = 0;
        this.listPrice = 0;
        this.stock = 0;
        this.used = false;
    }

    public LegacyProduct(String id, String name, String description, int price, int listPrice, int stock, boolean used) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.listPrice = listPrice;
        this.stock = stock;
        this.used = used;
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

    public boolean isUsed() {
        return used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegacyProduct that = (LegacyProduct) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
