package com.ark.JWT.With.UserDetails.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @NotBlank(message = "UserName is mandatory.")
    private String userName;
    @NotBlank(message = "First Name is mandatory.")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Password is mandatory.")
    private String password;
    private int age;
    @Length(min = 12, max = 12, message = "Mobile No should be like 0312-1234567")
    @Pattern(regexp = "^03[0-9]{2}-[0-9]{7}$", message = "Mobile No should be 0312-1234567")
    private String mobileNo;
    @Email(message = "Email is be valid")
    private String email;
    private String profile;
    private boolean enable = true;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private Collection<UserRole> userRoles = new ArrayList<>();

    public User() {
    }

    public User(String userName, String firstName, String lastName, String password, int age, String mobileNo, String email, String profile, boolean enable) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
        this.mobileNo = mobileNo;
        this.email = email;
        this.profile = profile;
        this.enable = enable;
    }

    public User(String userName, String firstName, String lastName, String password, int age, String mobileNo, String email, String profile, boolean enable, Collection<UserRole> userRoles) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
        this.mobileNo = mobileNo;
        this.email = email;
        this.profile = profile;
        this.enable = enable;
        this.userRoles = userRoles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Collection<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Collection<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        this.userRoles.forEach(userRole ->
                authorities.add(new SimpleGrantedAuthority(userRole.getRole().getRoleName()))
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
