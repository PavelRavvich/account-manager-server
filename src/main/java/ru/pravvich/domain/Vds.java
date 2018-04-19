package ru.pravvich.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vds")
@ToString(exclude = "accounts")
@EqualsAndHashCode(exclude = "accounts")
public class Vds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "vds", cascade = CascadeType.DETACH)
    private Collection<SocialAccount> accounts;

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

    @Column(name = "note")
    private String note;

}
