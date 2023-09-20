package com.blog.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.CategoryRepository;
import com.blog.entity.Category;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.CategoryDTO;
import com.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) {
		
		Category category = modelMapper.map(categoryDto, Category.class);
		Category category2 = categoryRepository.save(category);
		
		return modelMapper.map(category2, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId) {
		
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category save = categoryRepository.save(category);
		
		return modelMapper.map(save, CategoryDTO.class);
	}

	@Override
	public void deleteCatogory(Integer categoryId) {
		
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
        categoryRepository.delete(category);
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category Id",categoryId));
		return modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> cats = categories.stream().map((e)->modelMapper.map(e, CategoryDTO.class)).collect(Collectors.toList());
		
		return cats;
	}

}
