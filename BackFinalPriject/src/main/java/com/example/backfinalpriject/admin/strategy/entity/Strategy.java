package com.example.backfinalpriject.admin.strategy.entity;

import com.example.backfinalpriject.entity.AuditingFields;
import com.example.backfinalpriject.distinction.repository.Subject;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "strategy")
public class Strategy extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strategy_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "lecture_name")
    private String lectureName;

    @Column(name = "instructor_name")
    private String instructorName;

    @Column(name = "strategy_views")
    private Long views;

    @Column(name="image")
    private String image;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;



}
