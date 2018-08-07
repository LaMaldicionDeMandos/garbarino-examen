package co.garbarino.test.model;

/**
 * Created by boot on 07/06/2018.
 */
public class Review {
    private final String id;
    private final String user;
    private final String review;

    Review() {
        this.id = null;
        this.user = null;
        this.review = null;
    }

    public Review(String id, String user, String review) {
        this.id = id;
        this.user = user;
        this.review = review;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        return id != null ? id.equals(review.id) : review.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
