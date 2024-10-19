package com.hmg.springsecurity.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    /*TODO: aquí gestionamos los roles que tendría el usuario*/
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        /*TODO: retorna el password del usuario*/
        return this.password;
    }

    @Override
    public String getUsername() {
        /*TODO: retorna el nombre del usuario, para este ejemplo retornamos el email*/
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        /*TODO: indica que la cuenta del usuario no ha expirado*/
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        /*TODO: indica que la cuenta del usuario no ha sido bloqueada*/
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        /*TODO: indica que las credenciales del usuario no ha expirado*/
        return true;
    }

    @Override
    public boolean isEnabled() {
        /*TODO: indica si el usuario está habilitado*/
        return true;
    }
}
