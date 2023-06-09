package pets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "APP_PRIVILEGES")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    //TODO json ignore
    @JsonIgnore
    @JoinTable(
            name = "GROUPS_PRIVILEGES",
            joinColumns = @JoinColumn(name = "PRIVILEGE_ID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID")
    )
    List<Group> groups;
}
