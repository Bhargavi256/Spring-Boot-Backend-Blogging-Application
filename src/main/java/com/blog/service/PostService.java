package com.blog.service;

import java.util.List;

import com.blog.payloads.PostDTO;
import com.blog.payloads.PostResponse;

public interface PostService {

	PostDTO createPost(PostDTO postDto,Integer category_id,Integer user_id);
	
	PostDTO updatePost(PostDTO postDto,Integer post_id);
	
	void deletePost(Integer post_id);
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	PostDTO getPostById(Integer post_id);
	
	List<PostDTO> getPostsByCategory(Integer category_id);
	
	List<PostDTO> getPostsByUser(Integer user_id);
	
	List<PostDTO> searchPosts(String keyword);
}
