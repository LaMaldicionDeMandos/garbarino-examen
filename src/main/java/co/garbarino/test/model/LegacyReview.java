package co.garbarino.test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by boot on 07/06/2018.
 */
@Entity
@JsonIgnoreProperties(value = { "product_id" })
public class LegacyReview {
    @Id
    private final String id;
    private final String user;
    private final String review;
    private final String productId;

    LegacyReview() {
        this.id = null;
        this.user = null;
        this.review = null;
        this.productId = null;
    }

    public LegacyReview(String id, String user, String review, String productId) {
        this.id = id;
        this.user = user;
        this.review = review;
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getReview() {
        return review;
    }

    public String getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegacyReview that = (LegacyReview) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
