package com.workinandoutapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;
    String image;
    String userId;
    String password;
    @Temporal(TemporalType.TIMESTAMP)
    Date regDate;
    Date uptDate;

    @PrePersist
    protected void onCreate() {
        regDate = new Date(); // 현재 시간을 설정
    }
}
