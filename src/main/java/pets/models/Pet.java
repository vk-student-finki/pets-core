package pets.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PETS")
@Getter
@Setter
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION", length = 2048)
    private String description;

    @OneToMany(mappedBy = "pet", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Attribute> attributes;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @OneToMany(mappedBy = "pet", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Picture> pictures;

    @Column(name="ADDED_FOR_APPLICATION")
    private Boolean addedForApplication;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}



