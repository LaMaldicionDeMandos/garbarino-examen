package co.garbarino.test.services;

import co.garbarino.test.model.Product;
import co.garbarino.test.model.Review;
import co.garbarino.test.restclients.LegacyService;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by boot on 07/06/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    LegacyService legacyService;

    ProductsService service;

    @Before
    public void before() {
        this.service = new ProductsService(legacyService);
    }

    @Test
    public void when_product_is_null_then_return_optional_null() {
        Optional<Product> nullProduct = Optional.ofNullable(null);
        when(legacyService.findProduct(anyString())).thenReturn(nullProduct);
        Optional<Product> result = service.getProduct("a");
        Assert.assertEquals(nullProduct, result);
    }

    @Test
    public void when_reviews_is_null_then_return_optional_product_with_null_reviews() {
        Optional<Product> oProduct = Optional.ofNullable(new Product("a", null, null, 0, 0, 0));
        Optional<List<Review>> oReviews = Optional.ofNullable(null);
        when(legacyService.findProduct(anyString())).thenReturn(oProduct);
        when(legacyService.findReviews(anyString())).thenReturn(oReviews);
        Optional<Product> result = service.getProduct("a");
        assertNull(result.get().getReviews());
    }

    @Test
    public void when_reviews_is_empty_list_then_return_optional_product_with_empty_review_list() {
        Optional<Product> oProduct = Optional.ofNullable(new Product("a", null, null, 0, 0, 0));
        Optional<List<Review>> oReviews = Optional.ofNullable(Lists.newArrayList());
        when(legacyService.findProduct(anyString())).thenReturn(oProduct);
        when(legacyService.findReviews(anyString())).thenReturn(oReviews);
        Optional<Product> result = service.getProduct("a");
        assertNotNull(result.get().getReviews());
        assertTrue(result.get().getReviews().isEmpty());
    }
}
