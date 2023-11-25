package com.example.gameserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    @Length(min = 4, max = 50)
    private String username;

    @Column(nullable = false, length = 64)
    @Length(min = 4, max = 64)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 80)
    @Length(min = 1, max = 80)
    private String name;

    @Column(nullable = false, length = 80)
    @Length(min = 1, max = 80)
    private String surname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<GameReview> gameReviews;

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
