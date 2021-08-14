package com.codingdojo.productsandcatergories.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.productsandcatergories.models.Category;
import com.codingdojo.productsandcatergories.models.Product;
import com.codingdojo.productsandcatergories.repositories.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}
	
	public Product createOne(Product product) {
		return productRepository.save(product);
	}
	
	public Product getOne(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            return null;
        }
	}
	
	public void addCategory(Long id, Category category) {
		Product product = this.getOne(id);
		if(product!=null) {
			List<Category> currentCategories = product.getCategories();
			currentCategories.add(category);
			product.setCategories(currentCategories);
			productRepository.save(product);
		}
	}
	
	public List<Product> allProductsInCategory(Category category) {
		return productRepository.findAllByCategories(category);
	}
	
	public List<Product> productsNotInCategory(Category category) {
		return productRepository.findByCategoriesNotContains(category);
	}
}
