package pl.jsmus.OnlineShoppingCart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.jsmus.OnlineShoppingCart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
