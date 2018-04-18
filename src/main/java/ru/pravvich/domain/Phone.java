package ru.pravvich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "phone")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"accounts"})
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "phone", cascade = CascadeType.DETACH)
    private Collection<SocialAccount> accounts;

    @Column(name = "reg_date")
    private Timestamp regDate;

    @Column(name = "number")
    private String number;

    @Column(name = "operator_url")
    private String operatorUrl;

    @Column(name = "operator_login")
    private String operatorAccLogin;

    @Column(name = "operator_pass")
    private String operatorAccPassword;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

}
