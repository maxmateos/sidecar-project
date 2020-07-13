package com.project.sidecarhealth.dto;

import java.util.Objects;

public class PostRequestDTO {

  private String title;
  private String postBody;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPostBody() {
    return postBody;
  }

  public void setPostBody(String postBody) {
    this.postBody = postBody;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PostRequestDTO that = (PostRequestDTO) o;
    return Objects.equals(getTitle(), that.getTitle())
        && Objects.equals(getPostBody(), that.getPostBody());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTitle(), getPostBody());
  }
}
