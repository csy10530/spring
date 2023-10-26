package com.usc.service;

import com.usc.beans.User;
import com.usc.beans.UserProfile;
import com.usc.dao.UserDao;
import com.usc.http.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
  @Autowired
  UserDao userDao;

  @Autowired
  PasswordEncoder passwordEncoder;

  public Response register(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    List<UserProfile> profiles = new ArrayList<>();
    profiles.add(new UserProfile(2));
    user.setProfiles(profiles);

    System.out.println(user);
    userDao.save(user);

    return new Response(true);
  }

  public Response registerAdm(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    List<UserProfile> profiles = new ArrayList<>();
    profiles.add(new UserProfile(1));
    user.setProfiles(profiles);

    System.out.println(user);
    userDao.save(user);

    return new Response(true);
  }

  public Response changePassword(User user, Authentication authentication) {
    if (user.getUsername().equals(authentication.getName()) ||
            isAdmin(authentication.getAuthorities())) {
      User u = userDao.findByUsername(user.getUsername());
      u.setPassword(passwordEncoder.encode(user.getPassword()));
      userDao.save(u);
      return new Response(true);
    } else {
      return new Response(false);
    }
  }

  public boolean isAdmin(Collection<? extends GrantedAuthority> profiles) {
    boolean isAdmin = false;
    for (GrantedAuthority profile : profiles) {
      if (profile.getAuthority().equals("ROLE_ADMIN")) {
        isAdmin = true;
      }
    }

    return isAdmin;
  }

  public Response deleteUser(int id) {
    if (userDao.findById(id) != null) {
      userDao.deleteById(id);
      return new Response(true);
    } else {
      return new Response(false, "User not found!");
    }
  }
}
