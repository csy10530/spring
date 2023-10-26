package com.usc.SpringBootDemo.UserTests;

import com.usc.beans.User;
import com.usc.beans.UserProfile;
import com.usc.dao.UserDao;
import com.usc.http.Response;
import com.usc.service.UserService;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestUserService {
  @InjectMocks
  private UserService userService;

  @Mock
  private UserDao userDao;

  @Mock
  private PasswordEncoder passwordEncoder;

  private User user;
  private User admin;
  private List<User> users;

  @BeforeEach
  public void setUp() {
    this.admin = new User(1, "admin", "admin", Lists.newArrayList(new UserProfile(1)));
    this.user = new User(2, "user", "user", Lists.newArrayList(new UserProfile(2)));
    this.users = Lists.newArrayList(user, admin);
  }

  @AfterEach
  public void cleanUp() {
    this.user = null;
    this.admin = null;
    this.users = null;
  }

  @Test
  public void testAddUserService() {
    when(userDao.save(any(User.class))).thenReturn(this.user);

    userService.register(this.user);
    verify(userDao, times(1)).save(any(User.class));
  }

  @Test
  public void testGetAllUsersService() {
    userDao.save(user);
    userDao.save(admin);

    when(userDao.findAll()).thenReturn(this.users);

    List<User> response = userDao.findAll();

    assertThat(response.size()).isEqualTo(2);

    assertThat(response.get(0).getUsername()).isEqualTo("user");
    assertThat(response.get(0).getProfiles().get(0).getId()).isEqualTo(2);

    assertThat(response.get(1).getUsername()).isEqualTo("admin");
    assertThat(response.get(1).getProfiles().get(0).getId()).isEqualTo(1);
  }

  @Test
  public void testDeleteUser() {
    Response response = userService.deleteUser(user.getId());
    verify(userDao, times(1)).deleteById(2);
    assertThat(response.getCode()).isEqualTo(200);
  }
}
