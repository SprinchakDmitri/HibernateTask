package com.company.hibernateproj.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="t_user")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE t_user SET  user_fname = 'deleted',user_lname = 'deleted',email = 'deleted',user_nickname = 'deleted',ENABLED = false WHERE USER_ID =?")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name="user_fname")
    private String userFirstName;

    @Column(name="user_lname")
    private String userLastName;

    @Column(name="email",unique = true)
    private String email;

    @Column(name = "user_nickname",unique = true)
    String userName;

    @Column(name="created_at")
    private Date createdAt = Date.valueOf(LocalDate.now());

    @Column(name="enabled")
    private boolean enabled;



    @Enumerated(EnumType.STRING)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name="user_id")},
               inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> role = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Task> task;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Discipline discipline;


    public User(String userFirstName, String userLastName, String email, String userName, Date createdAt, boolean enabled,Discipline discipline) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.email = email;
        this.userName = userName;
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.discipline = discipline;
    }

    public User(String userFirstName, String userLastName, String email, String userName, Date createdAt, boolean enabled, List<Role> role, List<Task> task, Discipline discipline) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.email = email;
        this.userName = userName;
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.role = role;
        this.task = task;
        this.discipline = discipline;
    }
}

