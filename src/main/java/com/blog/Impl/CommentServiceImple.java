package com.blog.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.CommentRepository;
import com.blog.dao.PostRepository;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.CommentDTO;
import com.blog.service.CommentService;

@Service
public class CommentServiceImple implements CommentService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDTO createComment(CommentDTO commentDto, Integer post_id) {
		
		Post post = postRepository.findById(post_id).orElseThrow(()->new ResourceNotFoundException("post","postId",post_id));
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment save = commentRepository.save(comment);
		return modelMapper.map(save, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","CommentId",commentId));
		commentRepository.delete(comment);
	}

}
