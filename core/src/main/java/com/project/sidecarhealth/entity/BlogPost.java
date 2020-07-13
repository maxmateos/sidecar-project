package com.project.sidecarhealth.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class BlogPost {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;
  private String postBody;
  private Long userId;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
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
    BlogPost post = (BlogPost) o;
    return Objects.equals(getId(), post.getId())
        && Objects.equals(getTitle(), post.getTitle())
        && Objects.equals(getPostBody(), post.getPostBody())
        && Objects.equals(getUserId(), post.getUserId())
        && Objects.equals(getCreatedAt(), post.getCreatedAt())
        && Objects.equals(getUpdatedAt(), post.getUpdatedAt());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(), getTitle(), getPostBody(), getUserId(), getCreatedAt(), getUpdatedAt());
  }
}
