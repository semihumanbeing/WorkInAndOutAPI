package com.workinandoutapi.service;

import com.workinandoutapi.entity.User;
import com.workinandoutapi.entity.UserRepository;
import com.workinandoutapi.util.AESEncryptor;
import com.workinandoutapi.util.WebControlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WebControlServiceImpl implements WebControlService {
    private final UserRepository userRepository;
    private final WebControlUtil webControlUtil;
    private final AESEncryptor encryptor = new AESEncryptor();

    @Autowired
    public WebControlServiceImpl(UserRepository userRepository, WebControlUtil webControlUtil) {
        this.userRepository = userRepository;
        this.webControlUtil = webControlUtil;
    }


    @Override
    public boolean workIn(String userId) throws Exception {
        Optional<User> oUser = userRepository.findByUserId(userId);
        User user = oUser.orElseThrow(() -> new RuntimeException("Cannot find user information"));
        String password = encryptor.decrypt(user.getPassword());
        return webControlUtil.workInDaouOffice(userId, password);
    }

    @Override
    public boolean workOut(String userId) throws Exception {
        Optional<User> oUser = userRepository.findByUserId(userId);
        User user = oUser.orElseThrow(() -> new RuntimeException("Cannot find user information"));
        String password = encryptor.decrypt(user.getPassword());
        return webControlUtil.workOutDaouOffice(userId, password);
    }

    @Override
    public Map<String, Boolean> getStatus(String userId) throws Exception {
        Optional<User> oUser = userRepository.findByUserId(userId);
        User user = oUser.orElseThrow(() -> new RuntimeException("Cannot find user information"));
        String password = encryptor.decrypt(user.getPassword());
        return webControlUtil.checkWorkIn(userId, password);
    }
}
