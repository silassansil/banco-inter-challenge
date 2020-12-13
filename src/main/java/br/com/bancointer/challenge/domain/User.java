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

    @Lob
    private byte[] name;

    @Lob
    private byte[] email;

    @JoinColumn
    @OneToMany(cascade = CascadeType.ALL)
    private List<Calculation> calculations = new ArrayList<>();

    @Lob
    private byte[] privateKey;

    public User(byte[] name, byte[] email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }

    public User(String id, byte[] name, byte[] email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(byte[] name, byte[] email, byte[] privateKey) {
        this(name, email);
        this.privateKey = privateKey;
    }

    public void updateData(final User user) {
        this.privateKey = user.privateKey;
        this.calculations = user.calculations;
    }

    public void addCalculationsOnUser(final Calculation calculation) {
        this.calculations.add(calculation);
    }
}
