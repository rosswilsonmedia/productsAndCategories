package com.codingdojo.productsandcatergories.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.productsandcatergories.models.Category;
import com.codingdojo.productsandcatergories.models.Product;
import com.codingdojo.productsandcatergories.services.CategoryService;
import com.codingdojo.productsandcatergories.services.ProductService;

@Controller
public class CategoryController {

	
//	@InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        sdf.setLenient(true);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//    }
	
	private final ProductService productService;
	private final CategoryService categoryService;
	
	public CategoryController(ProductService productService, CategoryService categoryService) {
		this.productService=productService;
		this.categoryService=categoryService;
	}

	//view page
	@RequestMapping("/categories/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		Category category = categoryService.getOne(id);
		model.addAttribute("category", category);
		model.addAttribute("products", productService.productsNotInCategory(category));
		return "/categories/view.jsp";
	}
	
	//add category
	@PostMapping("/categories/{id}/add")
	public String addProduct(@PathVariable("id") Long id, @RequestParam("product") Long productId, RedirectAttributes redirectAttributes) {
		Category category = categoryService.getOne(id);
		if(category!=null) {
			Product product = productService.getOne(productId);
			if(product!=null) {
				categoryService.addProduct(id, product);
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "category not found");
		}
		return "redirect:/categories/" + id;
	}
	
	//new page
	@RequestMapping("/categories/new")
	public String newPage(@ModelAttribute("category") Category category) {
		return "/categories/new.jsp";
	}
	
	//add new
	@PostMapping("/categories")
	public String addNew(@ModelAttribute("category") Category category) {
		categoryService.createOne(category);
		return "redirect:/categories/new";
	}
}
