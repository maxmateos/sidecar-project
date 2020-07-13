package com.project.sidecarhealth.service.implementation;

import static com.project.sidecarhealth.constant.ErrorMessages.USER_RECORD_NOT_FOUND_MESSAGE;

import com.project.sidecarhealth.entity.User;
import com.project.sidecarhealth.exception.RecordNotFoundException;
import com.project.sidecarhealth.repository.UserRepository;
import com.project.sidecarhealth.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

  private UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User createUser(User user) {

    logger.info("Creating user in db");
    return userRepository.save(user);
  }

  @Override
  public User getUserById(Long id) {

    logger.info("Retrieving user with id: {}", id);
    return userRepository
        .findById(id)
        .orElseThrow(() -> new RecordNotFoundException(USER_RECORD_NOT_FOUND_MESSAGE));
  }

  @Override
  public List<User> getUsers() {

    logger.info("Retrieving all users from db");
    final Iterable<User> users = userRepository.findAll();
    return StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public List<User> findByLastname(String lastname) {

    logger.info("Retrieving all users with lastnama '{}' from db", lastname);
    final Iterable<User> users = userRepository.findByLastName(lastname);
    return StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public User updateUser(Long id, User user) {

    logger.info("Updating user with id: {}", id);
    final User userToUpdate =
        userRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(USER_RECORD_NOT_FOUND_MESSAGE));

    userToUpdate.setName(setValueIfNotNull(user.getName(), userToUpdate.getName()));
    userToUpdate.setLastName(setValueIfNotNull(user.getLastName(), userToUpdate.getLastName()));
    userToUpdate.setEmail(setValueIfNotNull(user.getEmail(), userToUpdate.getEmail()));
    userToUpdate.setApiKey(setValueIfNotNull(user.getApiKey(), userToUpdate.getApiKey()));

    return userRepository.save(userToUpdate);
  }

  @Override
  public void deleteUser(Long id) {

    logger.info("Deleting user with id: {}", id);
    final User userToDelete =
        userRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(USER_RECORD_NOT_FOUND_MESSAGE));
    userRepository.delete(userToDelete);
  }
}
