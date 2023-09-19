package com.workinandoutapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long idx;
    private String image;
    private String userId;
    private String password;
    private Timestamp regDate;
    private Timestamp uptDate;
    private Timestamp workDate;
    private boolean workIn;
    private boolean workOut;
}
