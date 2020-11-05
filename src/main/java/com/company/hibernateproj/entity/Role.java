package com.company.hibernateproj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;

import java.util.List;


@Data
@Entity
@Table(name = "t_role")
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private long id;

    @Column(name = "role_name")
    private String roleName;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany
    private List<User> userList= new ArrayList<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
