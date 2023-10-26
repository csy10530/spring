package com.usc.beans;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usc_user_profile")
public class UserProfile implements GrantedAuthority {
  private static final long serialVersionUID = 1L;

  @Id
  private int id;

  @Column
  private String type;

  public UserProfile() {
  }

  public UserProfile(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "UserProfile{" +
            "id=" + id +
            ", type='" + type + '\'' +
            '}';
  }

  @Override
  public String getAuthority() {
    return type;
  }
}
