package co.garbarino.test.resources;

import co.garbarino.test.model.Product;
import co.garbarino.test.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by boot on 07/06/2018.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class ProductsResource {
    private final static Logger log = LoggerFactory.getLogger(ProductsResource.class);

    private final ProductsService service;

    @Autowired
    public ProductsResource(final ProductsService service) {
        this.service = checkNotNull(service);
    }

    @RequestMapping(value = "/products/{id}", method = GET)
    public ResponseEntity<Product> getProduct(@PathVariable final String id) {
        try {
            Optional<Product> optional = service.getProduct(id);
            return optional.isPresent() ? ResponseEntity.ok(optional.get()) : ResponseEntity.notFound().build();
        } catch (HttpClientErrorException e) {
            log.info("Error finding legacy resources {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        }
        catch (RuntimeException e2) {
            log.info("Internal server error {}", e2.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
