package co.garbarino.test.resources;

import co.garbarino.test.model.LegacyProduct;
import co.garbarino.test.model.LegacyReview;
import co.garbarino.test.services.LegacyProductsService;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by boot on 07/06/2018.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "legacy", produces = APPLICATION_JSON_VALUE)
public class LegacyProductsResource {
    private final static Logger log = LoggerFactory.getLogger(LegacyProductsResource.class);

    private final LegacyProductsService service;

    @Autowired
    public LegacyProductsResource(final LegacyProductsService service) {
        this.service = checkNotNull(service);
    }

    @RequestMapping(value = "/reviews", method = GET)
    public ResponseEntity<List<LegacyReview>> getReviews(@RequestParam("product_id") final String productId) {
        try {
            return ResponseEntity.ok(service.findByProductId(productId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/products/{id}", method = GET)
    public ResponseEntity<LegacyProduct> getProduct(@PathVariable final String id) {
        try {
            Optional<LegacyProduct> optional = service.getProduct(id);
            return optional.isPresent() ? ResponseEntity.ok(optional.get()) : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
