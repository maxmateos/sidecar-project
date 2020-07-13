package com.project.sidecarhealth.transformer;

import com.project.sidecarhealth.dto.UserRequestDTO;
import com.project.sidecarhealth.dto.UserResponseDTO;
import com.project.sidecarhealth.dto.UsersResponseDTO;
import com.project.sidecarhealth.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserTransformers {

  public User transform(UserRequestDTO userRequestDTO) {

    final User user = new User();
    user.setName(userRequestDTO.getName());
    user.setLastName(userRequestDTO.getLastName());
    user.setEmail(userRequestDTO.getEmail());
    user.setApiKey(userRequestDTO.getApiKey());

    return user;
  }

  public UserResponseDTO transform(User user) {

    final UserResponseDTO userResponseDTO = new UserResponseDTO();
    userResponseDTO.setId(user.getId());
    userResponseDTO.setName(user.getName());
    userResponseDTO.setLastName(user.getLastName());
    userResponseDTO.setEmail(user.getEmail());
    userResponseDTO.setApiKey(user.getApiKey());
    userResponseDTO.setCreatedAt(user.getCreatedAt());
    userResponseDTO.setUpdatedAt(user.getUpdatedAt());

    return userResponseDTO;
  }

  public UsersResponseDTO transform(List<User> usersList) {

    final UsersResponseDTO usersResponseDTO = new UsersResponseDTO();
    final List<UserResponseDTO> userResponseDTOList =
        usersList.stream().map(this::transform).collect(Collectors.toList());

    usersResponseDTO.setUsersList(userResponseDTOList);
    return usersResponseDTO;
  }
}
