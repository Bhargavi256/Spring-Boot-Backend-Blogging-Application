package com.blog.service;

import java.util.List;

import com.blog.payloads.CategoryDTO;

public interface CategoryService {

	public CategoryDTO createCategory(CategoryDTO categoryDto);
	public CategoryDTO updateCategory(CategoryDTO categoryDto,Integer categoryId);;
	public void deleteCatogory(Integer categoryId);
	public CategoryDTO getCategory(Integer catogoryId);
	List<CategoryDTO> getAllCategories();
	
}
