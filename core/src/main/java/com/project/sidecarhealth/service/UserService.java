package com.project.sidecarhealth.service;

import com.project.sidecarhealth.entity.User;

import java.util.List;

public interface UserService {

  User createUser(User user);
  User getUserById(Long userId);
  List<User> getUsers();
  Long castUserIdToLong(String rawUserId);

}
