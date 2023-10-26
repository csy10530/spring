package com.usc.SpringBootDemo.UserTests;

import com.usc.beans.User;
import com.usc.beans.UserProfile;
import com.usc.controller.UserController;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestUserController {
  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  @Mock
  private UserDao userDao;

  private User user;
  private User admin;
  private List<User> users;

  @BeforeEach
  public void setUp() {
//    MockHttpServletRequest request = new MockHttpServletRequest();
//    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

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
  public void testAddUser() {
    when(userService.register(any(User.class))).thenReturn(new Response(true));
    Response response = userController.addUser(this.user);

    assertThat(response.getCode()).isEqualTo(200);
  }

  @Test
  public void testGetAllUsers() {
    when(userDao.findAll()).thenReturn(this.users);
    List<User> response = userController.getUsers();

    assertThat(response.size()).isEqualTo(2);

    assertThat(response.get(0).getUsername()).isEqualTo("user");
    assertThat(response.get(0).getProfiles().get(0).getId()).isEqualTo(2);

    assertThat(response.get(1).getUsername()).isEqualTo("admin");
    assertThat(response.get(1).getProfiles().get(0).getId()).isEqualTo(1);
  }

  @Test
  public void testChangeUser() {
    Authentication auth = mock(Authentication.class);

    when(userService.changePassword(any(User.class), any(Authentication.class)))
            .thenReturn(new Response(true));
    Response response = userController.changeUser(this.user, auth);

    assertThat(response.getCode()).isEqualTo(200);
  }

  @Test
  @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
  public void testDeleteUser() {
    when(userService.deleteUser(anyInt())).thenReturn(new Response(true));
    Response response = userController.deleteUser(this.user.getId());
    verify(userService, times(1)).deleteUser(2);
    assertThat(response.getCode()).isEqualTo(200);
  }
}
