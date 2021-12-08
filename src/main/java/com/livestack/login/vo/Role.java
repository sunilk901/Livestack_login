package com.livestack.login.vo;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Entity of a Security Role
 * Created by Mary Ellen BOwman
 */
public class Role  implements GrantedAuthority {
    private Long id;
    private String roleName;
    private String description;
    public String getAuthority() {
        return roleName;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}