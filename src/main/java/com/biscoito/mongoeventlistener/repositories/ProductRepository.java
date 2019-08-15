package com.biscoito.mongoeventlistener.repositories;

import com.biscoito.mongoeventlistener.domains.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ProductRepository extends CrudRepository<Product, String> {
}
