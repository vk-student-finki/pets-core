package dev.aucta.handgrenades.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ATTRIBUTES")
@Getter
@Setter
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VALUE")
    private String value;

    @ManyToOne
    @JoinColumn(name = "ATTRIBUTETYPE_ID")
    private AttributeType attributeType;

    @ManyToOne
    @JoinColumn(name = "GRENADE_ID")
    @JsonIgnore
    private Grenade grenade;
}
