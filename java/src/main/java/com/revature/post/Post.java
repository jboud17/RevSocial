package com.revature.post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.revature.user.UserNew;

@Entity
@Table(name = "POSTS")
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="POST_ID")
	@Expose
	private int postId;
	
	@Column(name="IMG_HASH")
	@Expose
	private String hash;
	
	@Column(name="POST_TEXT")
	@Expose
	private String post_text;	// what is the text of the post
	
	@Column(name="TITLE")
	@Expose
	private String title;
	
	@ManyToOne
    @JoinColumn(name="USER_ID", referencedColumnName="USER_ID")
	@Expose
	private UserNew user;

	public Post() {
	}
	
	public Post(String title, String imgHash, String post_text, UserNew user) {
		super();
		this.title = title;
		this.hash = imgHash;
		this.post_text = post_text;
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPost_id() {
		return postId;
	}

	public void setPost_id(int post_id) {
		this.postId = post_id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String imgHash) {
		this.hash = imgHash;
	}

	public String getPost_text() {
		return post_text;
	}

	public void setPost_text(String post_text) {
		this.post_text = post_text;
	}

	public UserNew getUser() {
		return user;
	}

	public void setUser(UserNew user) {
		this.user = user;
	}
	
}