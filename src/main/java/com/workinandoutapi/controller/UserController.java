package com.workinandoutapi.controller;

import com.workinandoutapi.dto.UserDTO;
import com.workinandoutapi.entity.User;
import com.workinandoutapi.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws Exception {
        if(userService.isUnique(user.getUserId())) {
            UserDTO userDTO = UserDTO.builder()
                    .userId(user.getUserId())
                    .password(user.getPassword())
                    .build();
            userService.registerUser(userDTO);
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already registered");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse response,
                                   @RequestBody User user) throws Exception {

        UserDTO userDTO = UserDTO.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .build();

        if(userService.loginUser(userDTO)){
            Cookie cookie = new Cookie("LOGINCOOKIE", user.getUserId());
            cookie.setMaxAge(60 * 60 * 24); // 하루
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
}
