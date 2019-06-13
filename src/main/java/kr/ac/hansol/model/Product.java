package kr.ac.hansol.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import javassist.SerialVersionUID;

@Entity
@Table(name="product")
public class Product implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5755400733831406061L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="product_id")
	private int id;
	
	@NotEmpty(message="The product name must not be null")
	private String name;
	private String category;
	
	@Min(value=0, message="The product price must not be less than zero")
	private Integer price;
	
	@NotEmpty(message="The product manufacturer must not be null")
	private String manufacturer;
	
	@Min(value=0,  message="The product unitInStock must not be less than zero")
	private Integer unitInStock;
	
	private String description;
	
	@Transient
	private MultipartFile productImage;
	
	private String imageFilename;

	public int getId() {
		return id;
	} 

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getUnitInStock() {
		return unitInStock;
	}

	public void setUnitInStock(Integer unitInStock) {
		this.unitInStock = unitInStock;
	}

	public String getDescription() {
		return description;
	}

	public MultipartFile getProductImage() {
		return productImage;
	}

	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
}
