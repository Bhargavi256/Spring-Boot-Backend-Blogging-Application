package com.blog.payloads;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.entity.Comment;

public class PostDTO {
	
	private Integer post_id;

	private String title;
	
	private String content;
	
    private String image_Name;
	
	private Date addedDate;
	
	private CategoryDTO category;
	
	private UserDTO user;
	
	private Set<CommentDTO> comments = new HashSet<>();


	

	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public Integer getPost_id() {
		return post_id;
	}

	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage_Name() {
		return image_Name;
	}

	public void setImage_Name(String image_Name) {
		this.image_Name = image_Name;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	

	

	public PostDTO(Integer post_id, String title, String content, String image_Name, Date addedDate,
			CategoryDTO category, UserDTO user, Set<CommentDTO> comments) {
		this.post_id = post_id;
		this.title = title;
		this.content = content;
		this.image_Name = image_Name;
		this.addedDate = addedDate;
		this.category = category;
		this.user = user;
		this.comments = comments;
	}

	public PostDTO() {
    	
    }
	
}
