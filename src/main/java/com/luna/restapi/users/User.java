package com.luna.restapi.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_seq",
            allocationSize = 1,
            sequenceName = "user_seq"
    )
    @GeneratedValue(generator = "user_seq")
    private Long id;

    @Column(unique = true, length = 50)
    private String email;
    private String name;
    private String lastname;
    private String password;

    @Column(name = "validation_code")
    private String validationCode;

    @Temporal(TemporalType.DATE)
    private Date createdAt;
    private boolean verified;

    @PrePersist
    private void date() {
        createdAt = new Date();
    }
}
