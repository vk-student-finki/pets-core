package pets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pets.auth.CustomUserDetails;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User extends CustomUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;

    @Transient
    private String newPassword;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name="PHONE_NUMBER")
    private String phoneNumber;

    @Column(name="MFA_KEY")
    private String mfaKey;

    @Column(name="MFA_ENABLED")
    private Boolean mfaEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_GROUPS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID")
    )
    private List<Group> groups = new ArrayList<>();

    @Transient
    private List<Privilege> privileges = new ArrayList<>();

    public User(User user) {
        super(user);
    }

    public User() {
    }
}