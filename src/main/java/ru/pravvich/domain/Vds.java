package ru.pravvich.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "vds")
@ToString(exclude = "accounts")
@EqualsAndHashCode(exclude = "accounts")
public class Vds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "vds", cascade = CascadeType.DETACH)
    private Collection<SocialAccount> accounts;

    @Column(name = "ip")
    private String ip;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "activated")
    private Timestamp activated;

    @Column(name = "deactivated")
    private Timestamp deactivated;

    @Column(name = "note")
    private String note;

}
