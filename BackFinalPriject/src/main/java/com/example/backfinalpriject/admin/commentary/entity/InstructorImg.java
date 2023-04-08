package com.example.backfinalpriject.admin.commentary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "instructor_img")
public class InstructorImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentary_id")
    private Commentary commentary;

    @Setter
    @Column(name = "original_file_name")
    private String originalFileName;

    @Setter
    @Column(name = "stored_file_name")
    private String storedFileName;

    @Setter
    @Column(name = "file_path")
    private String filePath;

    public static InstructorImg of(Commentary commentary, String originalFileName, String savedName, String filePath) throws IOException {
        InstructorImg instructorImg = new InstructorImg();
        instructorImg.setCommentary(commentary);
        instructorImg.setOriginalFileName(originalFileName);
        instructorImg.setStoredFileName(savedName);
        instructorImg.setFilePath(filePath);
        return instructorImg;
    }

    public void updateImg(String originalFileName, String savedName, String filePath){
        this.originalFileName = originalFileName;
        this.storedFileName = savedName;
        this.filePath = filePath;
    }
}
