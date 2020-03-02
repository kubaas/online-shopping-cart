package pl.jsmus.OnlineShoppingCart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.jsmus.OnlineShoppingCart.entity.OrderDetail;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, String> {

}
