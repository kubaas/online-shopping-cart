package pl.jsmus.OnlineShoppingCart.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.jsmus.OnlineShoppingCart.entity.Order;
import pl.jsmus.OnlineShoppingCart.entity.OrderDetail;
import pl.jsmus.OnlineShoppingCart.entity.Product;
import pl.jsmus.OnlineShoppingCart.model.CartInfo;
import pl.jsmus.OnlineShoppingCart.model.CartLineInfo;
import pl.jsmus.OnlineShoppingCart.model.CustomerInfo;
import pl.jsmus.OnlineShoppingCart.service.OrderDetailService;
import pl.jsmus.OnlineShoppingCart.service.OrderService;
import pl.jsmus.OnlineShoppingCart.utils.Utils;

@Controller
@RequestMapping("/shop")
public class CustomerController {

	private OrderService orderService;
	private OrderDetailService orderDetailService;
	
	@Autowired
	public CustomerController(OrderService theOrderService, OrderDetailService theOrderDetailService) {
		orderService=theOrderService;
		orderDetailService = theOrderDetailService;
	}
	
	//GET:Fill customer form
	@GetMapping("/cartCustomer")
	public String fillCustomerForm(HttpServletRequest request, Model theModel) {
		
		CartInfo cartInfo = Utils.getCartInSession(request);
		
		if(cartInfo == null) {
			return "redirect:/shop/cart";
		}
		
		CustomerInfo customerInfo = new CustomerInfo();
		
		
		theModel.addAttribute("customerInfo", customerInfo);
		
		return "shop/cart-customer";
	}
	
	//POST:Save customer form
	@PostMapping("/saveCustomer")
	public String saveCustomerForm(HttpServletRequest request, Model theModel,
			@ModelAttribute("customerInfo") @Validated CustomerInfo customerInfo,
			BindingResult result, final RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			customerInfo.setValid(false);
			//forward to refill customer form
			return "shop/cart-customer";
		}
		
		customerInfo.setValid(true);
		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.setCustomerInfo(customerInfo);
		
		return "redirect:/shop/cartConfirmation";
	}
	
	//GET: Show all information to confirm
	@GetMapping("/cartConfirmation")
	public String shoppingCartConfirmation(HttpServletRequest request, Model theModel) {
		
		CartInfo cartInfo = Utils.getCartInSession(request);
		
		if(cartInfo == null) {
			return "redirect:/shop/cart";
		}
		else if (!cartInfo.isValidCustomer()) {
			return "redirect:/shop/cartCustomer";
		}
		theModel.addAttribute("myCart", cartInfo);
		
		return "shop/cart-confirmation";
	}
	//POST: Confirm cart / save cart
	@PostMapping("/cartConfirmation")
	public String shoppingCartConfirmationSave(HttpServletRequest request, Model theModel) {
		
		CartInfo cartInfo = Utils.getCartInSession(request);
		
		if(cartInfo == null) {
			return "redirect:/shop/cart";
		}
		else if (!cartInfo.isValidCustomer()) {
			return "redirect:/shop/cartCustomer";
		}
		try {
			Order order = new Order();
			
			String id = UUID.randomUUID().toString();
			
			order.setId(id);
			order.setOrderNum(1);
			cartInfo.setOrderNum(1);
			order.setAmount(1);
			order.setCustomerAddress(cartInfo.getCustomerInfo().getAddress());
			order.setCustomerEmail(cartInfo.getCustomerInfo().getEmail());
			order.setCustomerName(cartInfo.getCustomerInfo().getName());
			order.setCustomerPhone(cartInfo.getCustomerInfo().getPhone());
			order.setOrderDate(new Date());
			orderService.save(order);
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setId(UUID.randomUUID().toString());
			orderDetail.setAmount(1);
			for(CartLineInfo line : cartInfo.getCartLines()) {
				orderDetail.setPrice(line.getAmount());
				orderDetail.setQuanity(line.getQuantity());
				Product product = new Product();
				product.setCode(line.getProductInfo().getCode());
				product.setCreateDate(new Date());
				product.setName(line.getProductInfo().getName());
				product.setPrice(line.getProductInfo().getPrice());
				orderDetail.setProduct(product);
				orderDetail.setOrder(order);
				orderDetailService.save(orderDetail);
				
			}
			
			
		} catch (Exception e) {
			
		}
		
		// Remove cart from session
		Utils.removeCartInSession(request);
		
		// Store last cart
		Utils.storeLastOrderedCartInSession(request, cartInfo);
		
		return "redirect:/shop/cartFinalize";
	}
	
	@GetMapping("/cartFinalize")
	public String shoppingCartFinalize(HttpServletRequest request, Model theModel) {
		
		CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
		
		if(lastOrderedCart == null) {
			return "redirect:/shop/cart";
		}
		theModel.addAttribute("lastOrderedCart", lastOrderedCart);
		return "shop/cart-finalize";
	}
}
