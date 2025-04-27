package com.user_service.controller;

import com.user_service.model.User;
import com.user_service.utill.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController {

    private final JwtUtil jwtUtil;

    private final List<User> list = new ArrayList<>();

    public UserController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        list.add(user);
        return user;
    }

    @GetMapping
    public List<User> getAllUser() {
        return list;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return list.stream().filter(user -> Objects.equals(user.id(), id)).findFirst().orElse(new User(null, null, null));

    }

    @GetMapping("/token")
    public Map<String, String> generateToken() {
        String generate = jwtUtil.generate("1", "ADMIN", "ACCESS");
        return Map.of("AccessToken", generate);
    }

}
