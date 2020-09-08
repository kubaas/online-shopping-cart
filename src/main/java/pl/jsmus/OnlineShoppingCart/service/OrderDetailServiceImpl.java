package pl.jsmus.OnlineShoppingCart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pl.jsmus.OnlineShoppingCart.dao.OrderDetailsRepository;
import pl.jsmus.OnlineShoppingCart.entity.OrderDetail;

/**
 * 
 * @author jakub
 *
 *	Service for connecting with orderDetailRepository(DAO) and using CRUD methods
 */

@Service
@Transactional
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

		return orderDetailRepository.findById(theId).orElseThrow(() -> new RuntimeException("Did not find order detail id - " + theId));
		
	}

	@Override
	public void save(OrderDetail theOrderDetail) {
		orderDetailRepository.save(theOrderDetail);
	}

	@Override
	public void deleteByOrder_id(String theId) { orderDetailRepository.deleteByOrder_id(theId);  }



}
