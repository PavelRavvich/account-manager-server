package ru.pravvich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "vds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "accounts")
public class Vds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "activated")
    private Timestamp activatedDate;

    @Column(name = "deactivated")
    private Timestamp deactivatedDate;

    @OneToMany(mappedBy = "vds", cascade = CascadeType.DETACH)
    private Set<SocialAccount> accounts;

    @Column(name = "note")
    private String note;

}
