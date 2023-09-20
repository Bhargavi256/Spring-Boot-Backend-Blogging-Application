package com.blog.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.blog.dao.CategoryRepository;
import com.blog.dao.PostRepository;
import com.blog.dao.UserRepository;
import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.PostDTO;
import com.blog.payloads.PostResponse;
import com.blog.service.FileService;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
	@Override
	public PostDTO createPost(PostDTO postDto,Integer user_id,Integer category_id) {
		
		User user = userRepository.findById(user_id).orElseThrow(()-> new ResourceNotFoundException("User","User id",user_id));
		Category category = categoryRepository.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category","CAtegoryId",category_id));
		
		
		
		Post post = modelMapper.map(postDto,Post.class);
		post.setImage_Name("default_png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post save = postRepository.save(post);
		return modelMapper.map(save, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDto, Integer post_id) {
		 Post post = postRepository.findById(post_id).orElseThrow(()->new ResourceNotFoundException("post","PostID",post_id));
         post.setTitle(postDto.getTitle());
         post.setContent(postDto.getContent());
         post.setImage_Name(postDto.getImage_Name());
         
         Post save = postRepository.save(post);
		return modelMapper.map(save, PostDTO.class);
	}

	@Override
	public void deletePost(Integer post_id) {
	
		 Post post = postRepository.findById(post_id).orElseThrow(()->new ResourceNotFoundException("post","PostID",post_id));
		 postRepository.delete(post);

	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}else
			sort = Sort.by(sortBy).descending();
		
		//Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		//Pageable page = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost = postRepository.findAll(page);
		List<Post> all = pagePost.getContent();
		List<PostDTO> list = all.stream().map(e->modelMapper.map(e,PostDTO.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(list);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDTO getPostById(Integer post_id) {
		Post post = postRepository.findById(post_id).orElseThrow(()->new ResourceNotFoundException("post","post Id",post_id));
		return modelMapper.map(post, PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostsByCategory(Integer category_id) {
	
		Category category = categoryRepository.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",category_id));
		List<Post> postlist = postRepository.findByCategory(category);
		List<PostDTO> postDtos = postlist.stream().map(e->modelMapper.map(e, PostDTO.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDTO> getPostsByUser(Integer user_id) {
		User user = userRepository.findById(user_id).orElseThrow(()->new ResourceNotFoundException("user","userId",user_id));
		List<Post> postlist = postRepository.findByUser(user);
		List<PostDTO> postDtos = postlist.stream().map(e->modelMapper.map(e, PostDTO.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {
		List<Post> list = postRepository.findByTitleContaining(keyword);
		List<PostDTO> postDtos = list.stream().map(e->modelMapper.map(e, PostDTO.class)).collect(Collectors.toList());

		return postDtos;
	}
	
	//post image upload
	
}
