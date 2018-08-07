package co.garbarino.test.repositories;

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
@ContextConfiguration(classes=LegacyReviewsRepositoryTest.class)
public class LegacyReviewsRepositoryTest {
    @Autowired
    LegacyReviewRepository repo;

    @Test
    public void should_get_all_reviews() {
        LegacyReview aa = new LegacyReview("aa", null, null, null);
        LegacyReview ab = new LegacyReview("ab", null, null, null);
        LegacyReview ba = new LegacyReview("ba", null, null, null);
        LegacyReview bb = new LegacyReview("bb", null, null, null);
        List<LegacyReview> reviews = Lists.newArrayList(repo.findAll());

        assertEquals(3, reviews.size());
        assertTrue(reviews.contains(aa));
        assertTrue(reviews.contains(ab));
        assertTrue(reviews.contains(ba));
        assertFalse(reviews.contains(bb));
    }

    @Test
    public void should_get_only_review_for_a_product() {
        LegacyReview aa = new LegacyReview("aa", null, null, null);
        LegacyReview ab = new LegacyReview("ab", null, null, null);
        LegacyReview ba = new LegacyReview("ba", null, null, null);
        List<LegacyReview> reviews = Lists.newArrayList(repo.findByProductId("a"));

        assertEquals(2, reviews.size());
        assertTrue(reviews.contains(aa));
        assertTrue(reviews.contains(ab));
        assertFalse(reviews.contains(ba));
    }

    @Test
    public void should_get_only_review_for_b_product() {
        LegacyReview aa = new LegacyReview("aa", null, null, null);
        LegacyReview ab = new LegacyReview("ab", null, null, null);
        LegacyReview ba = new LegacyReview("ba", null, null, null);
        List<LegacyReview> reviews = Lists.newArrayList(repo.findByProductId("b"));

        assertEquals(1, reviews.size());
        assertFalse(reviews.contains(aa));
        assertFalse(reviews.contains(ab));
        assertTrue(reviews.contains(ba));
    }

    @Test
    public void should_get_empty_list_if_there_is_not_review() {
        List<LegacyReview> reviews = Lists.newArrayList(repo.findByProductId("c"));

        assertEquals(0, reviews.size());
    }
}
