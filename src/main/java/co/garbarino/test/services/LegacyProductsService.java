package co.garbarino.test.services;

import co.garbarino.test.model.LegacyProduct;
import co.garbarino.test.model.LegacyReview;
import co.garbarino.test.repositories.LegacyProductRepository;
import co.garbarino.test.repositories.LegacyReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by boot on 07/06/2018.
 */
@Service
public class LegacyProductsService {
    private final LegacyReviewRepository reviewRepo;
    private final LegacyProductRepository productRepo;

    @Autowired
    public LegacyProductsService(final LegacyReviewRepository reviewRepo, final LegacyProductRepository productRepo) {
        this.reviewRepo = checkNotNull(reviewRepo);
        this.productRepo = checkNotNull(productRepo);
    }

    public List<LegacyReview> findByProductId(final String productId) {
        return reviewRepo.findByProductId(productId);
    }

    public Optional<LegacyProduct> getProduct(final String id) {
        return Optional.ofNullable(productRepo.findOne(id));
    }
}
