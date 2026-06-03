package com.library.library_management_system.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role;
    private LocalDate joinedDate;

    public User() {}

    public User(String name, String email, String password, String role, LocalDate joinedDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.joinedDate = joinedDate;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public LocalDate getJoinedDate() { return joinedDate; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setJoinedDate(LocalDate joinedDate) { this.joinedDate = joinedDate; }
}