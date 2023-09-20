package com.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.Constants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDTO;
import com.blog.payloads.PostResponse;
import com.blog.service.FileService;
import com.blog.service.PostService;




@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/users/{user_id}/categories/{category_id}/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto, @PathVariable Integer user_id,
			@PathVariable Integer category_id) {

		PostDTO post = postService.createPost(postDto, user_id, category_id);

		return new ResponseEntity<PostDTO>(post, HttpStatus.CREATED);

	}

	@GetMapping("/users/{user_id}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer user_id) {
		List<PostDTO> postsByUser = postService.getPostsByUser(user_id);
		return new ResponseEntity<List<PostDTO>>(postsByUser, HttpStatus.OK);
	}

	@GetMapping("/categories/{category_id}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer category_id) {
		List<PostDTO> postsByCategory = postService.getPostsByCategory(category_id);
		return new ResponseEntity<List<PostDTO>>(postsByCategory, HttpStatus.OK);
	}

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIR, required = false) String sortDir
) {
		PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	@GetMapping("/posts/{post_id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer post_id) {
		PostDTO postDTO = postService.getPostById(post_id);
		return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
	}

	@DeleteMapping("/posts/{post_id}")
	public ApiResponse deletePost(@PathVariable Integer post_id) {
		postService.deletePost(post_id);
		return new ApiResponse("succesfully deleted", true);
	}

	@PutMapping("/posts/{post_id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDto, @PathVariable Integer post_id) {
		PostDTO post = postService.updatePost(postDto, post_id);
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keywords") String keywords){
		List<PostDTO> posts = postService.searchPosts("%"+keywords+"%");
		return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
	}
	
	@PostMapping("/posts/image/upload/{post_id}")
	public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer post_id) throws IOException{
		
		String uploadImage = fileService.uploadImage(path, image);
		PostDTO postDTO = postService.getPostById(post_id);
		postDTO.setImage_Name(uploadImage);
		PostDTO updatePost = postService.updatePost(postDTO, post_id);
		return new ResponseEntity<PostDTO>(updatePost,HttpStatus.OK);
	}

	@GetMapping(value="/posts/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE
			)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		
		 response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}