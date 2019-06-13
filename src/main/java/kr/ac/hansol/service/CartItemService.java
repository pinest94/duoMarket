package kr.ac.hansol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansol.dao.CartItemDao;
import kr.ac.hansol.model.Cart;
import kr.ac.hansol.model.CartItem;

@Service
public class CartItemService {

	@Autowired
	private CartItemDao cartItemDao;
	
	public void addCartItem(CartItem cartItem) {
		cartItemDao.addCartItem(cartItem);
	}
	
	public void removeCartItem(CartItem cartItem) {
		cartItemDao.removeCartItem(cartItem);
	}
	public void removeAllCartItems(Cart cart) {
		cartItemDao.removeAllCartItems(cart);
	}
	public CartItem getCartItemByProductId(int cartId, int productId) {
		return cartItemDao.getCartItemByProductId(cartId, productId);
	}
	
}
