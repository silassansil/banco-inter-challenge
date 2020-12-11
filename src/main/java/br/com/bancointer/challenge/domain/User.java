package br.com.bancointer.challenge.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String name;
    private String email;

    @JoinColumn
    @OneToMany(cascade = CascadeType.ALL)
    private final List<Calculation> calculations = new ArrayList<>();

    public User(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void addCalculationsOnUser(final Calculation calculation) {
        this.calculations.add(calculation);
    }
}
