package com.facilities.pet.domain.user;

import com.facilities.pet.domain.BaseTimeEntity;
import com.facilities.pet.domain.enums.OauthProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * . User
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User extends BaseTimeEntity {

  @JsonIgnore
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(name = "username", length = 50, unique = true)
  private String username;

  @JsonIgnore
  @Column(name = "password", length = 100)
  private String password;

  @Column(name = "phone_number", length = 50)
  private String phoneNumber;

  private OauthProvider oauthProvider;

  @JsonIgnore
  @ManyToMany
  @JoinTable(
      name = "user_authority",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {
          @JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
  private Set<Authority> authorities;
}
