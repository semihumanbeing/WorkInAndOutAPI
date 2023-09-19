package com.workinandoutapi.service;

import com.workinandoutapi.entity.User;

public interface WebControlService {
    void workIn(User user);
    void workOut(User user);
}
