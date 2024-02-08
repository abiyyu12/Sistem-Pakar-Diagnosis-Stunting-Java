package com.project.es.data.libary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin extends AuditableEntity<String>{

    @Column(unique = true,nullable = false,length = 100)
    private String email;

    @Column(nullable = false,length = 150)
    private String password;

    @Column(name = "first_name",nullable = false,length = 50)
    private String firstName;

    @Column(name = "last_name",nullable = false,length = 50)
    private String lastName;

    @Column(name = "full_name",nullable = false,length = 100)
    private String fullName;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "admin_roles",joinColumns = @JoinColumn(name = "admin_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id"))
    private Collection<Role> roles;
}