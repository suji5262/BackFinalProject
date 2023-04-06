package com.example.backfinalpriject.admin.commentary.entity;

import com.example.backfinalpriject.admin.commentary.dto.CommentaryRequest;
import com.example.backfinalpriject.config.AuditingFields;
import com.example.backfinalpriject.distinction.entity.Subject;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "commentary")
public class Commentary extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentary_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Setter @Column(name = "instructor_name")
    private String instructorName;

    @Setter @Column(name = "title")
    private String title;

    @Setter
    @OneToOne(mappedBy = "commentary")
    private InstructorImg instructorImg;

    @Setter @OneToMany(mappedBy = "commentary", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentaryVideo> commentaryVideoList = new ArrayList<>();

    @Setter @OneToMany(mappedBy = "commentary", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentaryFile> commentaryFileList = new ArrayList<>();

    public static Commentary of(CommentaryRequest request, Subject subject){
        Commentary commentary = new Commentary();
        commentary.setSubject(subject);
        commentary.setInstructorName(request.getInstructorName());
        commentary.setTitle(request.getTitle());
        return commentary;
    }
}
