package com.workinandoutapi.controller;

import com.workinandoutapi.service.WebControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebController {

    private final WebControlService webControlService;

    @Autowired
    public WebController(WebControlService webControlService) {
        this.webControlService = webControlService;
    }

    @RequestMapping("/work-in")
    public ResponseEntity<?> workIn(@RequestBody String userId) {
        return null;
    }

    @RequestMapping("/work-out")
    public ResponseEntity<?> workOut(@RequestBody String userId) {
        return null;
    }

}
