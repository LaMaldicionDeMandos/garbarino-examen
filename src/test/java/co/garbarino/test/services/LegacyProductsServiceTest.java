package co.garbarino.test.services;

import co.garbarino.test.model.LegacyProduct;
import co.garbarino.test.model.LegacyReview;
import co.garbarino.test.repositories.LegacyProductRepository;
import co.garbarino.test.repositories.LegacyReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by boot on 07/06/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class LegacyProductsServiceTest {
    @Mock
    LegacyReviewRepository reviewRepo;

    @Mock
    LegacyProductRepository productRepo;

    LegacyProductsService service;

    @Before
    public void before() {
        this.service = new LegacyProductsService(reviewRepo, productRepo);
    }

    @Test
    public void when_get_reviews_by_product_id_should_call_repo_and_return_what_it_returns() {
        List<LegacyReview> reviews = asList(mock(LegacyReview.class));
        when(reviewRepo.findByProductId(anyString())).thenReturn(reviews);

        List<LegacyReview> result = service.findByProductId("a");

        verify(reviewRepo).findByProductId("a");
        assertEquals(reviews, result);
    }

    @Test
    public void when_get_product_by_id_should_call_repo_and_return_the_product_as_an_optional() {
        LegacyProduct product = mock(LegacyProduct.class);
        when(productRepo.findOne(anyString())).thenReturn(product);

        Optional<LegacyProduct> result = service.getProduct("a");

        verify(productRepo).findOne("a");
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    public void when_get_product_by_id_that_not_exists_should_call_repo_and_return_an_optional_with_null() {
        when(productRepo.findOne(anyString())).thenReturn(null);

        Optional<LegacyProduct> result = service.getProduct("a");

        verify(productRepo).findOne("a");
        assertNotNull(result);
        assertFalse(result.isPresent());
    }
}
