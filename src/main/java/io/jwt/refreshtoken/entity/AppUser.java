package io.jwt.refreshtoken.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    // Ne pas afficher le mot de passe quand on fait répond aux client via json type
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // appeler les roles quand on appelle AppUser avec l'option FetchType.EAGER
    // Bonne pratique : initier la collection à une ArrayList vide pour ne pas la laisser NULL
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> appRoles = new ArrayList<>();
}
