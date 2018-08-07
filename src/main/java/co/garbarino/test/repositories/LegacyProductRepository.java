package co.garbarino.test.repositories;

import co.garbarino.test.model.LegacyProduct;
import co.garbarino.test.model.LegacyReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by boot on 07/06/2018.
 */
@Repository
public interface LegacyProductRepository extends CrudRepository<LegacyProduct, String> {}
