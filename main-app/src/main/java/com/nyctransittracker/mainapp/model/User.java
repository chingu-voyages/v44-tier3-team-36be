package com.nyctransittracker.mainapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
// @Table(name="_user")
public class User implements UserDetails{

    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
        // throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }
    @Override
    public String getUsername() {
        return email;
        // throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
    }
    @Override
    public boolean isEnabled() {
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
    }

}
