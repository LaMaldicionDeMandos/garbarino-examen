package co.garbarino.test.resources;

import co.garbarino.test.model.Product;
import co.garbarino.test.model.Review;
import co.garbarino.test.services.ProductsService;
import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by boot on 07/06/2018.
 */
public class ProductsResourceTest extends AbstractResourceTest<ProductsResource> {
    @Mock
    ProductsService service;

    @Override
    protected ProductsResource createEndpoint() {
        return spy(new ProductsResource(service));
    }

    @Test
    public void get_to_product_should_call_the_service_and_return_200() throws Exception {
        when(service.getProduct(anyString())).thenReturn(Optional.of(new Product("a", null, null, 0, 0, 0)));
        mvc.perform(get("/products/a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(endpoint).getProduct("a");
    }

    @Test
    public void get_to_product_should_fail_with_404_if_not_set_product_id() throws Exception {
        mvc.perform(get("/products")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(endpoint, never()).getProduct("a");
    }

    @Test
    public void get_to_product_should_fail_with_500_if_service_fail() throws Exception {
        when(service.getProduct(anyString())).thenThrow(RuntimeException.class);
        mvc.perform(get("/products/a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(endpoint).getProduct("a");
    }

    @Test
    public void get_to_product_should_return_404_if_legacy_service_send_404_error() throws Exception {
        when(service.getProduct(anyString())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        mvc.perform(get("/products/a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(endpoint).getProduct("a");

    }

    @Test
    public void get_to_product_should_return_400_if_legacy_service_send_400_error() throws Exception {
        when(service.getProduct(anyString())).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        mvc.perform(get("/products/a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(endpoint).getProduct("a");

    }

    @Test
    public void get_products_should_correct_json() throws Exception {
        Product p = new Product("a", "Product A", "lalala", 100, 50, 10);
        p.setReviews(Lists.newArrayList(new Review("aa", "user", "lololo")));
        when(service.getProduct(anyString())).thenReturn(Optional.ofNullable(p));
        mvc.perform(get("/products/a")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.equalTo("a")))
                .andExpect(jsonPath("name", Matchers.equalTo("Product A")))
                .andExpect(jsonPath("description", Matchers.equalTo("lalala")))
                .andExpect(jsonPath("price", Matchers.equalTo(100)))
                .andExpect(jsonPath("listPrice", Matchers.equalTo(50)))
                .andExpect(jsonPath("stock", Matchers.equalTo(10)))
                .andExpect(jsonPath("reviews[0].id", Matchers.equalTo("aa")))
                .andExpect(jsonPath("reviews[0].user", Matchers.equalTo("user")))
                .andExpect(jsonPath("reviews[0].review", Matchers.equalTo("lololo")));

        verify(endpoint).getProduct("a");
    }
}
