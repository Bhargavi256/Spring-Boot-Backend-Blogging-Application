package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDTO;
import com.blog.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/posts/{post_id}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment,@PathVariable Integer post_id){
		CommentDTO commentDTO = commentService.createComment(comment, post_id);
		return new ResponseEntity<CommentDTO>(commentDTO,HttpStatus.OK);
	}
	
	@DeleteMapping("/comments/comment_id")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer comment_id){
         
	  commentService.deleteComment(comment_id);
	
	return new ResponseEntity<ApiResponse>(new ApiResponse("successful",true),HttpStatus.OK);
	}
}
