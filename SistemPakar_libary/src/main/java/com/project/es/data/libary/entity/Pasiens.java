package com.project.es.data.libary.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pasiens")
public class Pasiens extends AuditableEntity<String>{

    @Column(unique = true,nullable = false,length = 100)
    private String email;

    @Column(nullable = false,length = 255)
    private String password;

    @Column(name = "first_name",nullable = false,length = 50)
    private String firstName;

    @Column(name = "last_name",nullable = false,length = 50)
    private String lastName;

    @Column(name = "full_name",nullable = false,length = 100)
    private String fullName;

    @Column(name = "phone",nullable = false,length = 15)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "pasien_role",
            joinColumns = @JoinColumn(name = "pasien_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "pasiens",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Diagnosis> diagnoses;
}
