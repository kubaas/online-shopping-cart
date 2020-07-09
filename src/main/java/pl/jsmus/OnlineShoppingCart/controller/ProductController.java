package pl.jsmus.OnlineShoppingCart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.jsmus.OnlineShoppingCart.entity.Order;
import pl.jsmus.OnlineShoppingCart.entity.OrderDetail;
import pl.jsmus.OnlineShoppingCart.entity.Product;
import pl.jsmus.OnlineShoppingCart.model.CartInfo;
import pl.jsmus.OnlineShoppingCart.model.ProductInfo;
import pl.jsmus.OnlineShoppingCart.service.OrderDetailService;
import pl.jsmus.OnlineShoppingCart.service.OrderService;
import pl.jsmus.OnlineShoppingCart.service.ProductService;
import pl.jsmus.OnlineShoppingCart.utils.Utils;

@Controller
@RequestMapping("/shop")
public class ProductController {

	private ProductService productService;
	
	private OrderService orderService;
	
	private OrderDetailService orderDetailService;
	
	@Autowired
	public ProductController(ProductService theProductService, OrderService theOrderService, OrderDetailService theDetailService) {
		productService = theProductService;
		orderService = theOrderService;
		orderDetailService = theDetailService;
	}
	
	//add mapping for home page
	@GetMapping("/")
	public String listHomePage() {
		
		return "/shop/home";
	}
	
	
	// add mapping for "/list"
	@GetMapping("/list")
	public String listProducts(Model theModel) {
		
		List<Product> theProducts = productService.findAll();
		
		// add to the spring model
		theModel.addAttribute("products", theProducts);
		
		return "/shop/list-products";
	}
	
	
	@GetMapping("/orderList")
	public String listOrders(Model theModel) {
		
		List<Order> orders = new ArrayList<Order>();
		
		orders = orderService.findAll();
		
		theModel.addAttribute("orders", orders);
		
		return "/shop/order-list";
	}
	
	@GetMapping("/order")
	public String orderDetailsView(Model theModel, @RequestParam("orderId") String orderId) {
		Order order = null;
		
		if (orderId == null) {
			return "redirect:/shop/orderList";
		}

		order = orderService.findById(orderId);
		
		List<OrderDetail> orderDetails = orderDetailService.findAll();
		List<OrderDetail> orderDetails2 = new ArrayList<OrderDetail>();
		
		for(OrderDetail line : orderDetails) {
			System.out.println("LINE GETID: "+ line.getOrder().getId());
			System.out.println("ORDERID: " + orderId);
			if(line.getOrder().getId() == orderId) {
				
				orderDetails2.add(line);
			}
		}
		
		System.out.println(orderDetails2);
		
		theModel.addAttribute("orderInfo", order);
		theModel.addAttribute("details", orderDetails2);
		
		return "/shop/order";
	}

	@GetMapping("/orderDelete")
	public String orderDelete(Model theModel, @RequestParam("orderId") String orderId) {
		Order order = null;

		if (orderId == null) {
			return "redirect:/shop/orderList";
		}

		orderDetailService.deleteByOrder_id(orderId);
		orderService.deleteById(orderId);

		return "redirect:/shop/orderList";
	}
	
	
	@RequestMapping("/buyProduct")
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

	@RequestMapping("/deleteProduct")
	public String deleteProduct(HttpServletRequest request,
							 @RequestParam("productCode") String theCode) {

		Product theProduct = null;
		if(theCode != null && theCode.length() > 0) {
			theProduct = productService.findById(theCode);
			if(theProduct != null) {
				productService.deleteById(theCode);
			}
		}

		return "redirect:/shop/list";
	}
	
	//POST: Update quantity for product in cart
	@PostMapping("/cart")
	public String shoppingCartUpdate(HttpServletRequest request, Model theModel,
			@ModelAttribute("cartForm") CartInfo cartForm) {	
		
		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.updateQuantity(cartForm);
		
		return "redirect:/shop/cart";
	}
	
	//GET: Show cart
	@GetMapping("/cart")
	public String listCart(HttpServletRequest request, Model theModel) {	
		
		CartInfo myCart = Utils.getCartInSession(request);
		
		theModel.addAttribute("cartForm", myCart);
		
		return "/shop/cart";
	}
	
	//DELETE: Delete product from cart
	@RequestMapping("/cartRemoveProduct")
	public String removeProduct(HttpServletRequest request, Model theModel,
			@RequestParam(value = "code")String code) {
		
		Product product = null;
		
		if(code != null && code.length() > 0) {
			product = productService.findById(code);
		}
		
		if(product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.removeProduct(productInfo);
		}
		
		return "redirect:/shop/cart";
	}
}
