package ru.ncallie.JavaCase.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SecurityController {

    @PostMapping("/reg")
    public ResponseEntity registration() {
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/auth")
    public ResponseEntity authentication() {
        return ResponseEntity.ok().body("");
    }
}
