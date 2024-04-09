package com.mziuri;

import jakarta.persistence.*;
import org.apache.catalina.User;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chatrooms")
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "max_members")
    private int maxMembers;

    @OneToMany(mappedBy = "chatroom")
    private Set<User> members = new HashSet<>();


    public Chatroom(String name, int maxMembers) {
        this.name = name;
        this.maxMembers = maxMembers;
    }
}
