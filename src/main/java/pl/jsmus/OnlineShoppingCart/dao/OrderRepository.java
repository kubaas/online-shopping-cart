package pl.jsmus.OnlineShoppingCart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.jsmus.OnlineShoppingCart.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {


}
