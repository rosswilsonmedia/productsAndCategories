package com.codingdojo.productsandcatergories.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.productsandcatergories.models.Category;
import com.codingdojo.productsandcatergories.models.Product;
import com.codingdojo.productsandcatergories.repositories.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository=categoryRepository;
	}
	
	
	public Category createOne(Category category) {
		return categoryRepository.save(category);
	}
	
	
	public Category getOne(Long id) {
		Optional<Category> optionalProduct = categoryRepository.findById(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            return null;
        }
	}
	
	public void addProduct(Long id, Product product) {
		Category category = this.getOne(id);
		if(category!=null) {
			List<Product> currentProducts = category.getProducts();
			currentProducts.add(product);
			category.setProducts(currentProducts);
			categoryRepository.save(category);
		}
	}
	
	public List<Category> allCategoriesInProduct(Product product) {
		return categoryRepository.findAllByProducts(product);
	}
	
	public List<Category> categoriesNotInProduct(Product product) {
		return categoryRepository.findByProductsNotContains(product);
	}
}
