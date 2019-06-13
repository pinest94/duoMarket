package kr.ac.hansol.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansol.model.Cart;
import kr.ac.hansol.model.CartItem;
import kr.ac.hansol.model.Product;
import kr.ac.hansol.model.User;
import kr.ac.hansol.service.CartItemService;
import kr.ac.hansol.service.CartService;
import kr.ac.hansol.service.ProductService;
import kr.ac.hansol.service.UserService;

@RestController // @Controller + @ResponseBody
@RequestMapping("/api/cart")
public class CartRestController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	// cart 정보 조회
	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
	public ResponseEntity<Cart> getCartById(@PathVariable(value = "cartId") int cartId) {

		Cart cart = cartService.getCartById(cartId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("max-age=10");

		return new ResponseEntity<Cart>(cart, headers, HttpStatus.OK); // response entity의 body 부분에 cart 내용을 담아서 전달
	}

	// cart의 내용 전체 삭제
	@RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> clearCart(@PathVariable(value = "cartId") int cartId) {

		Cart cart = cartService.getCartById(cartId);
		cartItemService.removeAllCartItems(cart);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	// cart에 productId로 product 넣기
	@RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> addItem(@PathVariable(value = "productId") int productId) {

		Product product = productService.getProductById(productId);

		// 밑에 두줄 : 현재 인증된 사용자의 이름을 얻어올 수 있다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		User user = userService.getUserByUsername(username);
		Cart cart = user.getCart();

		// check if cartitem for a given product already exists
		List<CartItem> cartItems = cart.getCartItems();

		for (int i = 0; i < cartItems.size(); i++) {

			if (product.getId() == cartItems.get(i).getProduct().getId()) {

				CartItem cartItem = cartItems.get(i);

				cartItem.setQuantity(cartItem.getQuantity() + 1);
				cartItem.setTotalPrice(product.getPrice() * cartItem.getQuantity());

				cartItemService.addCartItem(cartItem);

				return new ResponseEntity<>(HttpStatus.OK);
			}
		}

		// create new cartItem
		CartItem cartItem = new CartItem();

		cartItem.setQuantity(1);
		cartItem.setTotalPrice(product.getPrice() * cartItem.getQuantity());
		cartItem.setProduct(product);
		cartItem.setCart(cart);

		// bidirectional
		cart.getCartItems().add(cartItem);

		cartItemService.addCartItem(cartItem);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// cart에 productId를 quantity minus or plus
	@RequestMapping(value = "/edit/{productId}/{quantity}", method = RequestMethod.PUT)
	public ResponseEntity<Void> editFromCart(@PathVariable(value = "productId") int productId,
			@PathVariable(value = "quantity") int quantity) {

		Product product = productService.getProductById(productId);

		// 밑에 두줄 : 현재 인증된 사용자의 이름을 얻어올 수 있다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		User user = userService.getUserByUsername(username);
		Cart cart = user.getCart();

		// check if cartitem for a given product already exists
		List<CartItem> cartItems = cart.getCartItems();

		for (int i = 0; i < cartItems.size(); i++) {

			if (product.getId() == cartItems.get(i).getProduct().getId()) {

				CartItem cartItem = cartItems.get(i);

				if (quantity == 1) {  // plus
					if ((cartItem.getQuantity() + 1) <= cartItem.getProduct().getUnitInStock()) {
						cartItem.setQuantity(cartItem.getQuantity() + 1);
						cartItem.setTotalPrice(product.getPrice() * cartItem.getQuantity());

						cartItemService.addCartItem(cartItem);

						return new ResponseEntity<>(HttpStatus.OK);
					} else {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					}
				} else if (quantity == -1) { // minus
					if ((cartItem.getQuantity() - 1) >= 1) {
						cartItem.setQuantity(cartItem.getQuantity() - 1);
						cartItem.setTotalPrice(product.getPrice() * cartItem.getQuantity());

						cartItemService.addCartItem(cartItem);

						return new ResponseEntity<>(HttpStatus.OK);
					} else {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					}
				}

			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// 해당되는 하나의 product를 cart에서 제거
	@RequestMapping(value = "/cartitem/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> removeItem(@PathVariable(value = "productId") int productId) {

		// 밑에 두줄 : 현재 인증된 사용자의 이름을 얻어올 수 있다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		User user = userService.getUserByUsername(username);
		Cart cart = user.getCart();

		CartItem cartItem = cartItemService.getCartItemByProductId(cart.getId(), productId);
		cartItemService.removeCartItem(cartItem);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
