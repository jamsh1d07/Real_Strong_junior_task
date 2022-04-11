package uz.pdp.corxona_telecom.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne// bitta user bitta companyga tegishli boladi
    private Company company;

    @Column(nullable = false)
    //@ElementCollection(fetch = FetchType.LAZY)
    private String role;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;




    // methodlar :

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        list.add(grantedAuthority);
       // List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>((Collection<? extends GrantedAuthority>) grantedAuthority);
        return  list;
    }


    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled; // default false
    }
}
