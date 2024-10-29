package com.base.new_base.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "number")
    private String number;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Order> orders;
    @ElementCollection
    @CollectionTable(name = "addresses", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "address")
    private List<String> addresses;
}