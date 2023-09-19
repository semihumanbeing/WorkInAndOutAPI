package com.workinandoutapi.service;

import com.workinandoutapi.entity.User;
import com.workinandoutapi.entity.UserRepository;
import com.workinandoutapi.util.WebControlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebControlServiceImpl implements WebControlService {
    private final UserRepository userRepository;
    WebControlUtil webControlUtil;

    @Autowired
    public WebControlServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void workIn(User user) {

    }

    @Override
    public void workOut(User user) {

    }
}
