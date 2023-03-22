package com.example.backfinalpriject.entity;


import com.example.backfinalpriject.distinction.entity.Subject;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "commentary")
public class Commentary extends AuditingFields{

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

    @Setter @Column(name = "instructor_img")
    private String instructorImg;

    @Setter @Column(name = "title")
    private String title;

    @Setter @OneToMany(mappedBy = "commentary", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentaryVideo> commentaryVideoList = new ArrayList<>();

    @Setter @OneToMany(mappedBy = "commentary", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentaryFile> commentaryFileList = new ArrayList<>();

    @Setter @Column(name = "views")
    private Long views;

    @Setter @Column(name = "fileAttached")
    private Integer fileAttached;

}
