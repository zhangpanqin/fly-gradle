package com.fly.study.gradle.user;

import com.fly.study.gradle.request.CreateUserRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/users")
    public String createUser(@RequestBody CreateUserRequest createUserRequest) {
        return createUserRequest.toString();
    }

    @GetMapping("/users/{id}")
    public String findUserById(@PathVariable String id) {
        return id;
    }

}
