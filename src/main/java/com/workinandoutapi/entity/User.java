package com.workinandoutapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity(name = "user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String image;
    private String userId;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    private Date uptDate;
    @Setter
    private Date workDate;
    @Setter
    private boolean workIn;
    @Setter
    private boolean workOut;


    @PrePersist
    protected void onCreate() {
        regDate = new Date();
    }
}
