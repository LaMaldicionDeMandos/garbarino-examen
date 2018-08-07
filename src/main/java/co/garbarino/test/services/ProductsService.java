package co.garbarino.test.services;

import co.garbarino.test.model.Product;
import co.garbarino.test.model.Review;
import co.garbarino.test.restclients.LegacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by boot on 07/06/2018.
 */
@Service
public class ProductsService {

    private final LegacyService service;

    @Autowired
    public ProductsService(final LegacyService service) {
        this.service = checkNotNull(service);
    }

    public Optional<Product> getProduct(final String id) {
        Optional<Product> oProduct = service.findProduct(id);
        if (!oProduct.isPresent()) return oProduct;
        Product product = oProduct.get();
        Optional<List<Review>> oReviews = service.findReviews(id);
        if (!oReviews.isPresent()) return oProduct;
        product.setReviews(oReviews.get());
        return Optional.of(product);
    }
}
