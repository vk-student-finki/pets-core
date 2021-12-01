package dev.aucta.handgrenades.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "ATTRIBUTE_TYPE")
@Getter
@Setter
public class AttributeType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

}
