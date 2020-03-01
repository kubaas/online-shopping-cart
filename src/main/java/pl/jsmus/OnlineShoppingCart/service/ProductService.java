package pl.jsmus.OnlineShoppingCart.service;

import java.util.List;

import pl.jsmus.OnlineShoppingCart.entity.Product;

public interface ProductService {

	public List<Product> findAll();
	
	public Product findById(String theId);
	
	public void save(Product theProduct);
	
	public void deleteById(String theId);
	
}
