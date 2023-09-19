package com.workinandoutapi.service;

import com.workinandoutapi.dto.UserDTO;

public interface UserService {
    void registerUser(UserDTO userDTO) throws Exception;
    boolean isUnique(String id);
    boolean loginUser(UserDTO userDTO) throws Exception;
}
