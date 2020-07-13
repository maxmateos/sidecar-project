package com.project.sidecarhealth.service;

import com.project.sidecarhealth.entity.BlogPost;
import java.util.List;

public interface PostService extends BaseService {

  BlogPost createPost(BlogPost post);

  List<BlogPost> getAllPosts();

  List<BlogPost> getUserPosts(Long userId);

  BlogPost getPostByIdAndUserId(Long postId, Long userId);

  BlogPost updatePost(Long postId, Long userId, BlogPost user);

  void deletePost(Long postId, Long userId);
}
