package ru.pravvich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

//@Entity
//@Table(name = "vds")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vds {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(name = "ip")
    private String ip;

//    @Column(name = "login")
    private String login;

//    @Column(name = "password")
    private String password;

//    @Column(name = "activated_date")
    private Timestamp activatedDate;

//    @Column(name = "deactivated_date")
    private Timestamp deactivatedDate;

//    @OneToMany(mappedBy = "vds", cascade = CascadeType.ALL)
    private Set<SocialAccount> accounts;

}
