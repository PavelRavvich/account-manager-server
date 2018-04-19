package ru.pravvich.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "social_account")
@ToString(exclude = {"vds", "phone"})
@EqualsAndHashCode(exclude = {"vds", "phone"})
public class SocialAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vds_id")
    private Integer vdsId;

    @Column(name = "phone_id")
    private Integer phoneId;

    @Column(name = "social_type")
    private String socialType;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "note")
    private String note;

    @Column(name = "reg_date")
    private Timestamp regDate;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "vds_id", insertable = false, updatable = false)
    private Vds vds;

    @ManyToOne
    @JoinColumn(name = "phone_id", insertable = false, updatable = false)
    private Phone phone;

}
