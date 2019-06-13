package kr.ac.hansol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.ac.hansol.dao.ProductDao;
import kr.ac.hansol.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	
	public List<Product> getProducts() {	
		return productDao.getProducts();
	}

	public void addProduct(Product product) {
		productDao.addProduct(product);
	}

	public void deleteProduct(Product product) {
		productDao.deleteProduct(product);
	}

	public Product getProductById(int id) {
		return productDao.getProductById(id);
	}

	public void updateProduct(Product product) {
		productDao.updateProduct(product);
	}

}
