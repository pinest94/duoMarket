package kr.ac.hansol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansol.model.Product;
import kr.ac.hansol.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/products")
	public String getProducts(Model model) {
	
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products);
		
		return "products"; // view's logical name
	}
	
	@RequestMapping("/productDetail/{productId}")
	public String detailProduct(@PathVariable int productId, Model model) {
	
		// image 가져오기
		Product product = productService.getProductById(productId);
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products);
		model.addAttribute("product", product);
		
		return "productDetail";
	}
}
