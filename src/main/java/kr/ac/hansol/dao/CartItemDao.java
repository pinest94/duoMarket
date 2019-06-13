package kr.ac.hansol.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansol.model.Cart;
import kr.ac.hansol.model.CartItem;

@Repository
@Transactional
public class CartItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addCartItem(CartItem cartItem) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cartItem);
		session.flush();
	}

	public void removeCartItem(CartItem cartItem) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(cartItem);
		session.flush();
	}

	public void removeAllCartItems(Cart cart) {
		List<CartItem> cartItems = cart.getCartItems(); // FetchType이 EAGER로 줬기 때문에 메모리 상에 올라와 있어서 이와 같이 읽기 가능
														// LAZY면 읽는 코드 추가 필요

		for (CartItem item : cartItems) {
			removeCartItem(item);
		}

	}

	@SuppressWarnings("unchecked")
	public CartItem getCartItemByProductId(int cartId, int productId) {
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<CartItem> query = session.createQuery("from CartItem where cart.id=?0 and product.id=?1");
		query.setParameter(0, cartId);
		query.setParameter(1, productId);
		
		return (CartItem) query.getSingleResult();
	}

}
