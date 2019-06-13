package kr.ac.hansol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansol.dao.CartDao;
import kr.ac.hansol.model.Cart;

@Service
public class CartService {

	@Autowired
	private CartDao cartDao;
	
	public Cart getCartById(int cartId) {
		return cartDao.getCartById(cartId);
	}
}
