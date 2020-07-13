package com.project.sidecarhealth.service;

import com.project.sidecarhealth.entity.User;
import java.util.List;

public interface UserService extends BaseService {

  User createUser(User user);

  User getUserById(Long id);

  List<User> getUsers();

  List<User> findByLastname(String lastname);

  User updateUser(Long id, User user);

  void deleteUser(Long id);
}
