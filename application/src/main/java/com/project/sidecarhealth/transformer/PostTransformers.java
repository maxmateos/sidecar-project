package com.project.sidecarhealth.transformer;

import com.project.sidecarhealth.dto.*;
import com.project.sidecarhealth.entity.BlogPost;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostTransformers {

  public BlogPost transform(Long userId, PostRequestDTO postRequestDTO) {

    final BlogPost post = new BlogPost();
    post.setTitle(postRequestDTO.getTitle());
    post.setPostBody(postRequestDTO.getPostBody());
    post.setUserId(userId);

    return post;
  }

  public PostResponseDTO transform(BlogPost post) {

    final PostResponseDTO postResponseDTO = new PostResponseDTO();
    postResponseDTO.setId(post.getId());
    postResponseDTO.setTitle(post.getTitle());
    postResponseDTO.setPostBody(post.getPostBody());
    postResponseDTO.setUserId(post.getUserId());
    postResponseDTO.setCreatedAt(post.getCreatedAt());
    postResponseDTO.setUpdatedAt(post.getUpdatedAt());

    return postResponseDTO;
  }

  public PostsResponseDTO transform(List<BlogPost> postsList) {

    final PostsResponseDTO postsResponseDTO = new PostsResponseDTO();
    final List<PostResponseDTO> postResponseDTOList =
        postsList.stream().map(this::transform).collect(Collectors.toList());

    postsResponseDTO.setPostsList(postResponseDTOList);
    return postsResponseDTO;
  }
}
