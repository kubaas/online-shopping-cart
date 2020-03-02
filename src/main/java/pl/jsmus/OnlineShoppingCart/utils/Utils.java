package pl.jsmus.OnlineShoppingCart.utils;

import javax.servlet.http.HttpServletRequest;

import pl.jsmus.OnlineShoppingCart.model.CartInfo;

public class Utils {

	//Products in the cart, stored in Session
	public static CartInfo getCartInSession(HttpServletRequest request) {
		
		CartInfo cartInfo = (CartInfo)request.getSession().getAttribute("myCart");
		
		if(cartInfo == null) {
			cartInfo = new CartInfo();
			
			request.getSession().setAttribute("myCart", cartInfo);
		}
		
		return cartInfo;
	}

	public static void removeCartInSession(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	public static void storeLastOrderedCartInSession(HttpServletRequest request, CartInfo cartInfo) {
		// TODO Auto-generated method stub
		
	}
}
