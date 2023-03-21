package com.example.backfinalpriject.distinction.dto;


import com.example.backfinalpriject.distinction.entity.Division;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DivisionDTO {

    private Long divisionId;

    private String divisionName;

    public DivisionDTO (Division division){
        this.divisionId = division.getId();
        this.divisionName = division.getDivisionName();
    }
}
