package com.project.sidecarhealth.service.implementation;

import com.project.sidecarhealth.entity.User;
import com.project.sidecarhealth.exception.RecordNotFoundException;
import com.project.sidecarhealth.repository.UserRepository;
import com.project.sidecarhealth.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.project.sidecarhealth.constant.ErrorMessages.INVALID_USER_ID_MESSAGE;
import static com.project.sidecarhealth.constant.ErrorMessages.USER_RECORD_NOT_FOUND_MESSAGE;

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
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        final Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new RecordNotFoundException(USER_RECORD_NOT_FOUND_MESSAGE));
    }

    @Override
    public List<User> getUsers() {
        final Iterable<User> users = userRepository.findAll();
        return StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Long castUserIdToLong(String rawUserId) {

        try {
            return Long.valueOf(rawUserId);
        } catch (NumberFormatException e) {
            logger.error("User id '{}' failed numeric format validation", rawUserId);
            throw new RecordNotFoundException(String.format(INVALID_USER_ID_MESSAGE, rawUserId));
        }
    }
}
