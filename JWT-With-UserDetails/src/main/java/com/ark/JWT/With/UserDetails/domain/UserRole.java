package com.ark.JWT.With.UserDetails.domain;

// Many UserRole can assign to Single User with Different Roles
// Many USerRole have A Single User and Role

import javax.persistence.*;

@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
