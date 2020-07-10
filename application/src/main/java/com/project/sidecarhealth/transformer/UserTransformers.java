package com.project.sidecarhealth.transformer;

import com.project.sidecarhealth.dto.CreateUserRequestDTO;
import com.project.sidecarhealth.dto.UserResponseDTO;
import com.project.sidecarhealth.dto.UsersResponseDTO;
import com.project.sidecarhealth.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserTransformers {

  public User transform(CreateUserRequestDTO createUserRequestDTO) {

    final User user = new User();
    user.setName(createUserRequestDTO.getName());
    user.setLastName(createUserRequestDTO.getLastName());
    user.setEmail(createUserRequestDTO.getEmail());
    user.setApiKey(createUserRequestDTO.getApiKey());

    return user;
  }

  public UserResponseDTO transform(User user) {

    final UserResponseDTO userResponseDTO = new UserResponseDTO();
    userResponseDTO.setId(user.getId());
    userResponseDTO.setName(user.getName());
    userResponseDTO.setLastName(user.getLastName());
    userResponseDTO.setEmail(user.getEmail());
    userResponseDTO.setApiKey(user.getApiKey());

    return userResponseDTO;
  }

  public UsersResponseDTO transform(List<User> usersList) {

    final UsersResponseDTO usersResponseDTO = new UsersResponseDTO();
    final List<UserResponseDTO> userResponseDTOList = usersList.stream()
            .map(this::transform)
            .collect(Collectors.toList());

    usersResponseDTO.setUsersList(userResponseDTOList);
    return usersResponseDTO;
  }
}
