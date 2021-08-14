package com.codingdojo.productsandcatergories.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.productsandcatergories.models.Category;
import com.codingdojo.productsandcatergories.models.Product;
import com.codingdojo.productsandcatergories.services.CategoryService;
import com.codingdojo.productsandcatergories.services.ProductService;

@Controller
public class ProductController {
	private final ProductService productService;
	private final CategoryService categoryService;
	
	public ProductController(ProductService productService, CategoryService categoryService) {
		this.productService=productService;
		this.categoryService=categoryService;
	}

	//view page
	@RequestMapping("/products/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		Product product = productService.getOne(id);
		model.addAttribute("product", product);
		model.addAttribute("categories", categoryService.categoriesNotInProduct(product));
		return "/products/view.jsp";
	}
	
	//add category
	@RequestMapping(value="/products/{id}/add", method=RequestMethod.POST)
	public String addCategory(@PathVariable("id") Long id, @RequestParam("category") Long categoryId, RedirectAttributes redirectAttributes) {
		Product product = productService.getOne(id);
		if(product!=null) {
			Category category = categoryService.getOne(categoryId);
			if(category!=null) {
				productService.addCategory(id, category);
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "product not found");
		}
		return "redirect:/products/" + id;
	}
	
	//new page
	@RequestMapping("/products/new")
	public String newPage(@ModelAttribute("product") Product product) {
		return "/products/new.jsp";
	}
	
	//add new
	@PostMapping("/products")
	public String addNew(@ModelAttribute("product") Product product) {
		productService.createOne(product);
		return "redirect:/products/new";
	}
}