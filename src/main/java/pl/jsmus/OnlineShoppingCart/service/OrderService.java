package pl.jsmus.OnlineShoppingCart.service;

import java.util.List;

import pl.jsmus.OnlineShoppingCart.entity.Order;


public interface OrderService {


	public List<Order> findAll();
	
	public Order findById(String theId);
	
	public void save(Order theOrder);
	
	public void deleteById(String theId);

}
