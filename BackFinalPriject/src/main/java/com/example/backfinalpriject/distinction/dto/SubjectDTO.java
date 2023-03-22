package com.example.backfinalpriject.distinction.dto;

import com.example.backfinalpriject.distinction.entity.Subject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubjectDTO {

    private Long subjectId;

    private String divisionName;

    private String subjectName;

    public SubjectDTO(Subject subject){
        this.subjectId = subject.getId();
        this.divisionName = subject.getDivision().getDivisionName();
        this.subjectName = subject.getSubjectName();

    }
}
