package dev.aucta.handgrenades.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PICTURES")
@Getter
@Setter
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "TYPE")
    private String type;

    @ManyToOne
    @JoinColumn(name = "GRENADE_ID")
    private Grenade grenade;
}
