package ru.pravvich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

//@Entity
//@Table(name = "phones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @OneToMany(mappedBy = "phone", cascade = CascadeType.ALL)
    private Set<SocialAccount> accounts;

    private String number;

    private String operatorType;

    private String operatorUrl;

    private Timestamp regDate;

    private String operatorAccLogin;

    private String operatorAccPassword;

}
