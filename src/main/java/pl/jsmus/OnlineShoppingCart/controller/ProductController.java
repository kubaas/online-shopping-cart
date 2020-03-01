package pl.jsmus.OnlineShoppingCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.jsmus.OnlineShoppingCart.entity.Product;
import pl.jsmus.OnlineShoppingCart.service.ProductService;

@Controller
@RequestMapping("/shop")
public class ProductController {

	private ProductService productService;
	
	@Autowired
	public ProductController(ProductService theProductService) {
		productService = theProductService;
	}
	
	// add mapping for "/list"
	
	@GetMapping("/list")
	public String listProducts(Model theModel) {
		
		List<Product> theProducts = productService.findAll();
		
		// add to the spring model
		theModel.addAttribute("products", theProducts);
		
		return "/shop/list-products";
	}
}
