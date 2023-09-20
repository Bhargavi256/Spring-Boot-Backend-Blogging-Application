package com.blog.service;

import com.blog.payloads.CommentDTO;

public interface CommentService {

	CommentDTO createComment(CommentDTO commentDto,Integer post_id);
	void deleteComment(Integer commentId);
}
