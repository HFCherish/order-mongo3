package com.thoughtworks.ketsu.domain.products;

import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Optional;

import static com.thoughtworks.ketsu.support.TestHelper.productJsonForTest;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(DatabaseTestRunner.class)
public class ProductRepositoryTest {
    @Inject
    ProductRepository productRepository;

    @Test
    public void should_create_and_save_product() {
        Product save = productRepository.save(productJsonForTest());
        Optional<Product> fetched = productRepository.findById(save.id);

        assertThat(fetched.isPresent(), is(true));
        assertThat(fetched.get().id, is(save.id));
    }
}