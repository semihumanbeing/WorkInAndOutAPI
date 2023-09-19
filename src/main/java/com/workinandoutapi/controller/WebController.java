package com.workinandoutapi.controller;

import com.workinandoutapi.dto.UserDTO;
import com.workinandoutapi.service.WebControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WebController {

    private final WebControlService webControlService;

    @Autowired
    public WebController(WebControlService webControlService) {
        this.webControlService = webControlService;
    }

    @PostMapping("/work-in")
    public ResponseEntity<?> workIn(@RequestBody UserDTO dto) throws Exception {
        Map<String, Boolean> res = new HashMap<>();
        res.put("result", webControlService.workIn(dto.getUserId()));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/work-out")
    public ResponseEntity<?> workOut(@RequestBody UserDTO dto) throws Exception {
        Map<String, Boolean> res = new HashMap<>();
        res.put("result", webControlService.workOut(dto.getUserId()));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/sync-status")
    public ResponseEntity<?> syncStatus(@RequestBody UserDTO dto) throws Exception {
        Map<String, Boolean> status = webControlService.getStatus(dto.getUserId());
        System.out.println(status);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

}
