package pl.jsmus.OnlineShoppingCart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.jsmus.OnlineShoppingCart.entity.Product;
import pl.jsmus.OnlineShoppingCart.model.CartInfo;
import pl.jsmus.OnlineShoppingCart.model.ProductInfo;
import pl.jsmus.OnlineShoppingCart.service.ProductService;
import pl.jsmus.OnlineShoppingCart.utils.Utils;

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
	
	@GetMapping("/createProduct")
	public String createProduct() {	
		return "/shop/create-product";
	}
	
	@GetMapping("/orderList")
	public String listOrders() {	
		return "/shop/order-list";
	}
	
	@GetMapping("/cart")
	public String listCart(HttpServletRequest request, Model theModel) {	
		
		CartInfo myCart = Utils.getCartInSession(request);
		
		theModel.addAttribute("cartForm", myCart);
		
		return "/shop/cart";
	}
	
	@GetMapping("/buyProduct")
	public String buyProduct(HttpServletRequest request,
			@RequestParam("productCode") String theCode, Model theModel) {
		
		Product theProduct = null;
		if(theCode != null && theCode.length() > 0) {
			theProduct = productService.findById(theCode);
		}
		
		if(theProduct != null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			
			ProductInfo productInfo = new ProductInfo(theProduct);
			
			cartInfo.addProduct(productInfo, 1);
		}
		
		return "redirect:/shop/cart";
	}
}
