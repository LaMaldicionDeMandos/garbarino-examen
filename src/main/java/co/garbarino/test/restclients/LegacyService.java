package co.garbarino.test.restclients;

import co.garbarino.test.model.Product;
import co.garbarino.test.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.http.HttpMethod.GET;

/**
 * Created by boot on 07/06/2018.
 */
@Component
public class LegacyService {
    private final static Logger log = LoggerFactory.getLogger(LegacyService.class);
    private final static String URL_PRODUCT_TEMPLATE = "%s/products/%s";
    private final static String URL_REVIEWS_TEMPLATE = "%s/reviews?product_id=%s";

    private final static ParameterizedTypeReference<List<Review>> reviewListType = new ParameterizedTypeReference<List<Review>>() {};

    private final RestTemplate restTemplate;
    private final String legacyUrl;

    @Autowired
    LegacyService(final RestTemplate restTemplate, @Value("${app.legacyUrl}") final String host)  {
        this.restTemplate = checkNotNull(restTemplate);
        this.legacyUrl = host;
    }

    @CacheEvict(allEntries = true, cacheNames = { "products", "reviews" })
    @Scheduled(fixedRateString = "${app.cache.ttl}")
    public void cacheEvict() {}

    @Cacheable("products")
    public Optional<Product> findProduct(final String id) {
        ResponseEntity<Product> restExchange =
                restTemplate.exchange(String.format(URL_PRODUCT_TEMPLATE, legacyUrl, id), GET, createVoidEntity(),
                        Product.class);
        log.info("get to product response status: " + restExchange.getStatusCodeValue());
        return Optional.ofNullable(restExchange.getBody());
    }

    @Cacheable("reviews")
    public Optional<List<Review>> findReviews(final String productId) {
        ResponseEntity<List<Review>> restExchange =
                restTemplate.exchange(String.format(URL_REVIEWS_TEMPLATE, legacyUrl, productId), GET, createVoidEntity(),
                        reviewListType);
        log.info("get to reviews response status: " + restExchange.getStatusCodeValue());
        return Optional.ofNullable(restExchange.getBody());
    }

    protected HttpEntity<Void> createVoidEntity() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(null, headers);
    }
}
