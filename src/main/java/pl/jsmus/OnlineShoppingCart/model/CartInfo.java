package pl.jsmus.OnlineShoppingCart.model;

import java.util.ArrayList;
import java.util.List;

public class CartInfo {

	private int orderNum;
	
	private CustomerInfo customerInfo;
	
	private List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();
	
	public CartInfo() {
		
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}
	
	public List<CartLineInfo> getCartLines() {
		return cartLines;
	}

	private CartLineInfo findLineByCode(String code) {
		for(CartLineInfo line : this.cartLines) {
			if(line.getProductInfo().getCode().equals(code)) {
				return line;
			}
		}
		return null;
	}

	public void addProduct(ProductInfo productInfo, int quantity) {
		
		CartLineInfo line = this.findLineByCode(productInfo.getCode());
		
		if(line == null) {
			line = new CartLineInfo();
			line.setQuantity(0);
			line.setProductInfo(productInfo);
			this.cartLines.add(line);
		}
		int newQuantity = line.getQuantity() + quantity;
		if(newQuantity <= 0) {
			this.cartLines.remove(line);
		}
		else {
			line.setQuantity(newQuantity);
		}

		
	}

	public void updateQuantity(CartInfo cartForm) {
		if(cartForm != null) {
			List<CartLineInfo> lines = cartForm.getCartLines();
			for(CartLineInfo line : lines) {
				this.updateProduct(line.getProductInfo().getCode(), line.getQuantity());
			}
		}
		
	}

	public void updateProduct(String code, int quantity) {
		CartLineInfo line = this.findLineByCode(code);
		
		if(line!=null) {
			if(quantity <= 0) {
				this.cartLines.remove(line);
			}
			else {
				line.setQuantity(quantity);
			}
		}
		
	}

	public void removeProduct(ProductInfo productInfo) {
		CartLineInfo line = this.findLineByCode(productInfo.getCode());
		
		if(line != null) {
			this.cartLines.remove(line);
		}
		
	}

	public boolean isValidCustomer() {
		return this.customerInfo != null && this.customerInfo.isValid();
	}
	
	public int getQuantityTotal() {
		int quantity = 0;
		for (CartLineInfo line : this.cartLines) {
			quantity += line.getQuantity();
		}
		return quantity;
	}
	
	public double getAmountTotal() {
		double amount = 0;
		for (CartLineInfo line : this.cartLines) {
			amount += line.getAmount();
		}
		return amount;
	}
	
	
	
}
