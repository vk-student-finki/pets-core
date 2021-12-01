package dev.aucta.handgrenades.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "attributeType", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Attribute> attributes;
}
