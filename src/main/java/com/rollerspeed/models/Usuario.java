package com.rollerspeed.models;

import java.util.Collection;

import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "users")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NaturalId
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(nullable = false, length = 200)
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @Column(nullable = false)
    private boolean enabled = true;
    
    public Usuario() {}
    
    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    // Setters 
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
