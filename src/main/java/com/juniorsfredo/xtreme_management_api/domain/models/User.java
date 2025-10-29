package com.juniorsfredo.xtreme_management_api.domain.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Person implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setDefaultPassword() {
        String cpf = this.getCpf();
        String name = this.getName();

        if (cpf == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        String digitsOnlyCpf = cpf.replaceAll("\\D+", "");

        if (digitsOnlyCpf.length() < 3) {
            throw new IllegalArgumentException("Invalid CPF");
        }

        String first3Cpf = digitsOnlyCpf.substring(0, 3);

        String firstName = name.trim().split("\\s+")[0];

        String defaultPassword = first3Cpf + "@" + firstName;

        setPassword(defaultPassword);
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
