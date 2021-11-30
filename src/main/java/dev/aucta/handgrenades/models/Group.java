package dev.aucta.handgrenades.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "GROUPS")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="USERS_GROUPS",
            joinColumns = @JoinColumn( name="GROUP_ID"),
            inverseJoinColumns = @JoinColumn( name="USER_ID")
    )
    List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="GROUPS_PRIVILEGES",
            joinColumns = @JoinColumn( name="GROUP_ID"),
            inverseJoinColumns = @JoinColumn( name="PRIVILEGE_ID")
    )
    List<Privilege> privileges;
}
