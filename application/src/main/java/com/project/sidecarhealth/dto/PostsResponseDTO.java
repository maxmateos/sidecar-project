package com.project.sidecarhealth.dto;

import java.util.List;
import java.util.Objects;

public class PostsResponseDTO {

  private List<PostResponseDTO> postsList;

  public List<PostResponseDTO> getPostsList() {
    return postsList;
  }

  public void setPostsList(List<PostResponseDTO> postsList) {
    this.postsList = postsList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PostsResponseDTO that = (PostsResponseDTO) o;
    return Objects.equals(getPostsList(), that.getPostsList());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPostsList());
  }
}
