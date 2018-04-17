package ru.pravvich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

//@Entity
//@Table(name = "social_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialAccount {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int vdsId;

    private String phoneNumber;

//    @ManyToOne
//    @JoinColumn(name = "vds_id")
    private Vds vds;

//    @Column(name = "social_type")
    private String socialType;

//    @Column(name = "login")
    private String login;

//    @Column(name = "password")
    private String password;

//    @Column(name = "notes")
    private String notes;

//    @OneToMany
//    @JoinColumn(name = "phone_id")
    private Phone phone;

//    @Column(name = "reg_date")
    private Timestamp regDate;

//    @Column(name = "status")
    private String status;

}
