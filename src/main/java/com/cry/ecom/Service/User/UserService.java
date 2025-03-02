package com.cry.ecom.Service.User;

import com.cry.ecom.Dto.UserDto;
import com.cry.ecom.Entity.User;
import com.cry.ecom.Dto.Request.CreateUserRequest;
import com.cry.ecom.Dto.Request.UserUpdateRequest;

public interface UserService {

    User getUserById(Long userId);

    User createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
