package com.project.sidecarhealth.service.implementation;

import static com.project.sidecarhealth.constant.ErrorMessages.POST_RECORD_NOT_FOUND_MESSAGE;
import static com.project.sidecarhealth.constant.ErrorMessages.USER_RECORD_NOT_FOUND_MESSAGE;

import com.project.sidecarhealth.entity.BlogPost;
import com.project.sidecarhealth.exception.RecordNotFoundException;
import com.project.sidecarhealth.repository.PostRepository;
import com.project.sidecarhealth.service.PostService;
import com.project.sidecarhealth.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostServiceImpl implements PostService {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PostServiceImpl.class);

  private PostRepository postRepository;
  private UserService userService;

  @Autowired
  public PostServiceImpl(PostRepository postRepository, UserService userService) {
    this.postRepository = postRepository;
    this.userService = userService;
  }

  @Override
  public BlogPost createPost(BlogPost post) {

    logger.info("Creating post in db");
    userService.getUserById(post.getUserId());
    return postRepository.save(post);
  }

  @Override
  public BlogPost getPostByIdAndUserId(Long postId, Long userId) {

    logger.info("Retrieving post with id: {}", postId);
    return postRepository
        .findByIdAndUserId(postId, userId)
        .orElseThrow(() -> new RecordNotFoundException(POST_RECORD_NOT_FOUND_MESSAGE));
  }

  @Override
  public List<BlogPost> getAllPosts() {

    logger.info("Retrieving all posts from db");
    final Iterable<BlogPost> posts = postRepository.findAll();
    return StreamSupport.stream(posts.spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public List<BlogPost> getUserPosts(Long userId) {

    logger.info("Retrieving all posts for user '{}' from db", userId);
    return postRepository.findByUserId(userId);
  }

  @Override
  public BlogPost updatePost(Long postId, Long userId, BlogPost post) {

    logger.info("Updating post with id: {}", postId);
    final BlogPost postToUpdate =
        postRepository
            .findByIdAndUserId(postId, userId)
            .orElseThrow(() -> new RecordNotFoundException(POST_RECORD_NOT_FOUND_MESSAGE));

    postToUpdate.setTitle(setValueIfNotNull(post.getTitle(), postToUpdate.getTitle()));
    postToUpdate.setPostBody(setValueIfNotNull(post.getPostBody(), postToUpdate.getPostBody()));

    return postRepository.save(postToUpdate);
  }

  @Override
  public void deletePost(Long postId, Long userId) {

    logger.info("Deleting post with id: {}", postId);
    final BlogPost postToDelete =
        postRepository
            .findByIdAndUserId(postId, userId)
            .orElseThrow(() -> new RecordNotFoundException(USER_RECORD_NOT_FOUND_MESSAGE));
    postRepository.delete(postToDelete);
  }
}
