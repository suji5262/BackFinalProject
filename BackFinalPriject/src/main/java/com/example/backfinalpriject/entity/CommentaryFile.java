package com.example.backfinalpriject.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file")
public class CommentaryFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentary_id")
    private Commentary commentary;

    @Setter @Column(name = "original_file_name")
    private String originalFileName;

    @Setter @Column(name = "stored_file_name")
    private String storedFileName;

    @Setter @Column(name = "file_path")
    private String filePath;

    @Setter @Column(name = "download_count")
    private Long downloadCount;

}
