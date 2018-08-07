package co.garbarino.test.resources;

import co.garbarino.test.model.LegacyProduct;
import co.garbarino.test.model.LegacyReview;
import co.garbarino.test.services.LegacyProductsService;
import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by boot on 07/06/2018.
 */
public class LegacyProductsResourceTest extends AbstractResourceTest<LegacyProductsResource> {
    @Mock
    LegacyProductsService service;

    @Override
    protected LegacyProductsResource createEndpoint() {
        return Mockito.spy(new LegacyProductsResource(service));
    }

    @Test
    public void get_to_reviews_should_call_the_service_and_return_200() throws Exception {
        mvc.perform(get("/legacy/reviews")
                .param("product_id", "a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(endpoint).getReviews("a");
    }

    @Test
    public void get_to_reviews_should_fail_with_400_if_not_set_product_id() throws Exception {
        mvc.perform(get("/legacy/reviews")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(endpoint, never()).getReviews("a");
    }

    @Test
    public void get_to_reviews_should_fail_with_500_if_service_fail() throws Exception {
        when(service.findByProductId(anyString())).thenThrow(RuntimeException.class);
        mvc.perform(get("/legacy/reviews")
                .param("product_id", "a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(endpoint).getReviews("a");
    }

    @Test
    public void get_to_reviews_should_return_empty_list_if_service_return_empty_list() throws Exception {
        when(service.findByProductId(anyString())).thenReturn(Lists.newArrayList());
        mvc.perform(get("/legacy/reviews")
                .param("product_id", "a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        verify(endpoint).getReviews("a");

    }

    @Test
    public void get_to_reviews_should_return_list_without_product_id_property() throws Exception {
        LegacyReview review1 = new LegacyReview("aa", "user", "lalala", "a");
        LegacyReview review2 = new LegacyReview("ab", "user2", "lalala", "a");
        when(service.findByProductId(anyString())).thenReturn(Lists.newArrayList(review1, review2));
        mvc.perform(get("/legacy/reviews")
                .param("product_id", "a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo("aa")))
                .andExpect(jsonPath("$[0].user", Matchers.equalTo("user")))
                .andExpect(jsonPath("$[0].review", Matchers.equalTo("lalala")))
                .andExpect(jsonPath("$[0].product_id").doesNotExist())

                .andExpect(jsonPath("$[1].id", Matchers.equalTo("ab")))
                .andExpect(jsonPath("$[1].user", Matchers.equalTo("user2")))
                .andExpect(jsonPath("$[1].review", Matchers.equalTo("lalala")))
                .andExpect(jsonPath("$[1].product_id").doesNotExist());

        verify(endpoint).getReviews("a");
    }

    @Test
    public void get_products_should_call_the_service_and_return_200() throws Exception {
        LegacyProduct p = new LegacyProduct("a", null, null, 0, 0, 0, false);
        when(service.getProduct(anyString())).thenReturn(Optional.ofNullable(p));
        mvc.perform(get("/legacy/products/a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(endpoint).getProduct("a");
    }

    @Test
    public void get_products_should_return_404_if_not_set_an_id() throws Exception {
        mvc.perform(get("/legacy/products")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(endpoint, never()).getProduct(anyString());
    }

    @Test
    public void get_products_should_fail_with_500_if_service_fail() throws Exception {
        when(service.getProduct(anyString())).thenThrow(RuntimeException.class);
        mvc.perform(get("/legacy/products/a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(endpoint).getProduct("a");
    }

    @Test
    public void get_products_should_return_404_if_not_found_product() throws Exception {
        when(service.getProduct(anyString())).thenReturn(Optional.ofNullable(null));
        mvc.perform(get("/legacy/products/a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(endpoint).getProduct("a");
    }

    @Test
    public void get_products_should_correct_json() throws Exception {
        LegacyProduct p = new LegacyProduct("a", "Product A", "lalala", 100, 50, 10, true);
        when(service.getProduct(anyString())).thenReturn(Optional.ofNullable(p));
        mvc.perform(get("/legacy/products/a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.equalTo("a")))
                .andExpect(jsonPath("name", Matchers.equalTo("Product A")))
                .andExpect(jsonPath("description", Matchers.equalTo("lalala")))
                .andExpect(jsonPath("price", Matchers.equalTo(100)))
                .andExpect(jsonPath("listPrice", Matchers.equalTo(50)))
                .andExpect(jsonPath("stock", Matchers.equalTo(10)))
                .andExpect(jsonPath("used", Matchers.equalTo(true)));

        verify(endpoint).getProduct("a");
    }
}
