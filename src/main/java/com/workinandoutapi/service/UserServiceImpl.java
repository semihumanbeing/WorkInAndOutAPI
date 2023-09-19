package com.workinandoutapi.service;

import com.workinandoutapi.dto.UserDTO;
import com.workinandoutapi.entity.User;
import com.workinandoutapi.entity.UserRepository;
import com.workinandoutapi.util.AESEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final WebControlService webControlService;
    AESEncryptor encryptor = new AESEncryptor();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, WebControlService webControlService) {
        this.userRepository = userRepository;
        this.webControlService = webControlService;
    }

    @Override
    public void registerUser(UserDTO userDTO) throws Exception {
        User user = User.builder()
                .userId(userDTO.getUserId())
                .password(encryptor.encrypt(userDTO.getPassword()))
                .build();
        userRepository.save(user);
    }
    @Override
    public boolean loginUser(UserDTO userDTO) throws Exception {
        Optional<User> userOptional = userRepository.findByUserId(userDTO.getUserId());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            String decPassword = encryptor.decrypt(user.getPassword());
            return userDTO.getPassword().equals(decPassword);
        }
        return false;
    }

    @Override
    public boolean isUnique(String userId) {
        return userRepository.findByUserId(userId).isEmpty();
    }

}
