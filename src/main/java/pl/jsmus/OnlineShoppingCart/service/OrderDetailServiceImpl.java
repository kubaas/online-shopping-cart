package pl.jsmus.OnlineShoppingCart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pl.jsmus.OnlineShoppingCart.dao.OrderDetailsRepository;
import pl.jsmus.OnlineShoppingCart.entity.OrderDetail;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	OrderDetailsRepository orderDetailRepository;
	
	public OrderDetailServiceImpl(OrderDetailsRepository theDetailsRepository) {
		orderDetailRepository = theDetailsRepository;
	}
	
	
	@Override
	public List<OrderDetail> findAll() {
		return orderDetailRepository.findAll();
	}

	@Override
	public OrderDetail findById(String theId) {
		
		Optional<OrderDetail> result = orderDetailRepository.findById(theId);
		
		OrderDetail theOrderDetail = null;
		
		if(result.isPresent()) {
			theOrderDetail = result.get();
		}
		else {
			throw new RuntimeException("Did not find order detail id - " + theId);
		}
		
		
		return theOrderDetail;
	}

	@Override
	public void save(OrderDetail theOrderDetail) {
		orderDetailRepository.save(theOrderDetail);
	}

	@Override
	public void deleteById(String theId) {
		orderDetailRepository.deleteById(theId);
	}

}
