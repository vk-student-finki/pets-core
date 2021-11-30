package dev.aucta.handgrenades.auth;

import dev.aucta.handgrenades.models.Group;
import dev.aucta.handgrenades.models.Privilege;
import dev.aucta.handgrenades.models.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;
    private List<Group> groups;
    private Long userId;

    public CustomUserDetails(){}

    public CustomUserDetails(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.groups = user.getGroups();
        this.authorities = translate(user.getPrivileges());
    }

    /**
     * Translates the List<Role> to a List<GrantedAuthority>
     *
     * @param privileges the input list of roles.
     * @return a list of granted authorities
     */

    private Collection<? extends GrantedAuthority> translate(List<Privilege> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Privilege privilege : privileges) {
            String name = privilege.getName().toUpperCase();
            //Make sure that all roles start with "ROLE_"
            if (!name.startsWith("ROLE_"))
                name = "ROLE_" + name;
            authorities.add(new SimpleGrantedAuthority(name));
        }
        return authorities;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}