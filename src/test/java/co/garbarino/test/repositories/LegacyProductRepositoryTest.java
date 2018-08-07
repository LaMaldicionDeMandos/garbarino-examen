package co.garbarino.test.repositories;

import co.garbarino.test.model.LegacyProduct;
import co.garbarino.test.model.LegacyReview;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by boot on 07/06/2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@EnableJpaRepositories(basePackages="co.garbarino.test.repositories")
@EntityScan(basePackages="co.garbarino.test.model")
@ContextConfiguration(classes=LegacyProductRepositoryTest.class)
public class LegacyProductRepositoryTest {
    @Autowired
    LegacyProductRepository repo;

    @Test
    public void should_get_all_products() {
        LegacyProduct a = new LegacyProduct("a", null, null, 0, 0, 0, false);
        LegacyProduct b = new LegacyProduct("b", null, null, 0,0,0, false);
        LegacyProduct c = new LegacyProduct("c", null, null, 0,0,0, false);
        List<LegacyProduct> products = Lists.newArrayList(repo.findAll());

        assertEquals(2, products.size());
        assertTrue(products.contains(a));
        assertTrue(products.contains(b));
        assertFalse(products.contains(c));
    }

    @Test
    public void should_get_only_a_product_a() {
        LegacyProduct result = repo.findOne("a");

        assertEquals("a", result.getId());
        assertEquals("Product A", result.getName());
    }

    @Test
    public void should_get_only_a_product_b() {
        LegacyProduct result = repo.findOne("b");

        assertEquals("b", result.getId());
        assertEquals("Product B", result.getName());
    }

    @Test
    public void should_get_null_if_there_is_not_product() {
        LegacyProduct p = repo.findOne("c");

        assertNull(p);
    }
}
