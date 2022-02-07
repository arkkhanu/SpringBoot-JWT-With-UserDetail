package com.ark.JWT.With.UserDetails.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @NotBlank(message = "Role is mandatory")
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "role")
    private Collection<UserRole> userRoles = new ArrayList<>();

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, Collection<UserRole> userRoles) {
        this.roleName = roleName;
        this.userRoles = userRoles;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Collection<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Collection<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
