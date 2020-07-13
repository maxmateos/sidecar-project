package com.project.sidecarhealth.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class PostResponseDTO {

  private Long id;
  private String title;
  private String postBody;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PostResponseDTO that = (PostResponseDTO) o;
    return Objects.equals(getId(), that.getId())
        && Objects.equals(getTitle(), that.getTitle())
        && Objects.equals(getPostBody(), that.getPostBody())
        && Objects.equals(getUserId(), that.getUserId())
        && Objects.equals(getCreatedAt(), that.getCreatedAt())
        && Objects.equals(getUpdatedAt(), that.getUpdatedAt());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(), getTitle(), getPostBody(), getUserId(), getCreatedAt(), getUpdatedAt());
  }
}
