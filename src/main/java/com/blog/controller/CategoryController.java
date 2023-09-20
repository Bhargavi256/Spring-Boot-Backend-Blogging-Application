package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDTO;
import com.blog.service.CategoryService;



@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	ResponseEntity<CategoryDTO> createCategory(@Validated @RequestBody CategoryDTO categoryDto){
		CategoryDTO categoryDTO2 = categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDTO>(categoryDTO2,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	ResponseEntity<CategoryDTO> updateCategory(@Validated @RequestBody CategoryDTO categoryDto,@PathVariable Integer categoryId){
		CategoryDTO categoryDTO2 = categoryService.updateCategory(categoryDto,categoryId);
		return new ResponseEntity<CategoryDTO>(categoryDTO2,HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		 categoryService.deleteCatogory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully!!!",true),HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer categoryId){
		 CategoryDTO category = categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDTO>(category,HttpStatus.OK);
	}
	
	@GetMapping("/")
	ResponseEntity<List<CategoryDTO>> getAllCategories(){
		 List<CategoryDTO> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}

}
