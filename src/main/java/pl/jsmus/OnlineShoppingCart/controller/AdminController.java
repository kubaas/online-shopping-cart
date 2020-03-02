package pl.jsmus.OnlineShoppingCart.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.jsmus.OnlineShoppingCart.entity.Product;
import pl.jsmus.OnlineShoppingCart.service.ProductService;

@Controller
@RequestMapping("/shop")
public class AdminController {
	
	ProductService productService;
	
	public AdminController(ProductService theProductService) {
		productService = theProductService;
	}

	// GET: Show product form to create new product
	@GetMapping("/createProduct")
	public String productForm(Model theModel) {
		
		Product product = new Product();
		
		theModel.addAttribute("productForm", product);
		
		return "shop/create-product";
	}
	
	// POST: Save created product
	@PostMapping("/createProduct")
	public String productFormSave(Model theModel, @ModelAttribute("productForm") @Validated Product theProduct, BindingResult result, final RedirectAttributes redirectAttributes) {
		
		theProduct.setCreateDate(new Date());
		
		if(result.hasErrors()) {
			return "/shop/create-product";
		}
		try {
			productService.save(theProduct);
		} catch (Exception e) {
			return "/shop/create-product";
		}
		
		return "redirect:/shop/list";
	}
	
}
