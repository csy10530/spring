package com.usc.controller;

import com.usc.beans.User;
import com.usc.dao.UserDao;
import com.usc.http.Response;
import com.usc.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController() // accept API request, returns json by default
@RequestMapping("/users")
public class UserController {
  @Autowired
  UserDao userDao;

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping
  public List<User> getUsers() {
    return userDao.findAll();
  }

  @PostMapping
  public Response addUser(@RequestBody User user) { // json
    return userService.register(user);
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
  @PutMapping
  public Response changeUser(@RequestBody User user, Authentication authentication) {
    return userService.changePassword(user, authentication);
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public Response deleteUser(@PathVariable int id) {
    return userService.deleteUser(id);
  }
}
