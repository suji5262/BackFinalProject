package com.example.backfinalpriject.admin.commentary.dto.response;

import com.example.backfinalpriject.admin.commentary.entity.Commentary;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SearchResponse {

    private Long commentaryId;
    private String subjectName;
    private String instructorName;
    private String instructorImg;
    private String commentaryTitle;

    public SearchResponse(Commentary commentary) {
        this.commentaryId = commentary.getId();
        this.subjectName = commentary.getSubject().getSubjectName();
        this.instructorName = commentary.getInstructorName();
        this.instructorImg = commentary.getInstructorImg().getFilePath();
        this.commentaryTitle = commentary.getTitle();
    }

}


