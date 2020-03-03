package pl.jsmus.OnlineShoppingCart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.jsmus.OnlineShoppingCart.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

	// add a method to sort by order number
	public List<Order> findAllByOrderByOrderNumAsc();
}
