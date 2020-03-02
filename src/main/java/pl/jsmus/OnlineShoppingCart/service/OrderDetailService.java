package pl.jsmus.OnlineShoppingCart.service;

import java.util.List;

import pl.jsmus.OnlineShoppingCart.entity.OrderDetail;


public interface OrderDetailService {

	public List<OrderDetail> findAll();
	
	public OrderDetail findById(String theId);
	
	public void save(OrderDetail theOrderDetail);
	
	public void deleteById(String theId);
}
