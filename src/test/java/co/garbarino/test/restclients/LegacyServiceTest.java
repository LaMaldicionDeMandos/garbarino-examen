package co.garbarino.test.restclients;

import co.garbarino.test.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.GET;

/**
 * Created by boot on 07/06/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class LegacyServiceTest {
    @Mock
    RestTemplate restTemplate;

    LegacyService service;

    @Before
    public void before() {
        service = spy(new LegacyService(restTemplate, "http://localhost:8080/legacy"));
    }

    @Test
    public void should_generate_a_well_formed_request_for_product() {
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Product.class)))
                .thenReturn(mock(ResponseEntity.class));

        service.findProduct("a");

        verify(restTemplate)
                .exchange(eq("http://localhost:8080/legacy/products/a"), eq(GET), any(), eq(Product.class));
    }
}
