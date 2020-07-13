package com.project.sidecarhealth.dto;

import java.util.List;
import java.util.Objects;

public class UsersResponseDTO {

  private List<UserResponseDTO> usersList;

  public List<UserResponseDTO> getUsersList() {
    return usersList;
  }

  public void setUsersList(List<UserResponseDTO> usersList) {
    this.usersList = usersList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UsersResponseDTO that = (UsersResponseDTO) o;
    return Objects.equals(getUsersList(), that.getUsersList());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUsersList());
  }
}
