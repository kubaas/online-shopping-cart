package pl.jsmus.OnlineShoppingCart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.jsmus.OnlineShoppingCart.dao.OrderRepository;
import pl.jsmus.OnlineShoppingCart.entity.Order;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;
	
	@Autowired
	public OrderServiceImpl(OrderRepository theOrderRepository) {
		orderRepository = theOrderRepository;
	}
	
	@Override
	public List<Order> findAll() {
		return orderRepository.findAllByOrderByOrderNumAsc();
	}

	@Override
	public Order findById(String theId) {
		Optional<Order> result = orderRepository.findById(theId);
		
		Order theOrder = null;
		
		if(result.isPresent()) {
			theOrder = result.get();
		}
		else {
			// we did not find the order
			throw new RuntimeException("Did not find order id - " + theId);
		}
		
		return theOrder;
	}

	@Override
	public void save(Order theOrder) {
		orderRepository.save(theOrder);
	}

	@Override
	public void deleteById(String theId) {
		orderRepository.deleteById(theId);
	}

}
