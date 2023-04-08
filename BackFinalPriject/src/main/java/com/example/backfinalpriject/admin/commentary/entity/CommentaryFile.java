package com.example.backfinalpriject.admin.commentary.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commentary_file")
public class CommentaryFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @Column(name = "download_count")
    private Long downloadCount;

    @Setter
    @Column(name = "file_path")
    private String filePath;

    public static CommentaryFile of(Commentary commentary, String originalFileName, String storedFileName, String filePath){
        CommentaryFile commentaryFile = new CommentaryFile();
        commentaryFile.setCommentary(commentary);
        commentaryFile.setOriginalFileName(originalFileName);
        commentaryFile.setStoredFileName(storedFileName);
        commentaryFile.setDownloadCount(0L);
        commentaryFile.setFilePath(filePath);
        return commentaryFile;
    }

    public void download(){
        this.downloadCount += 1;
    }

    public CommentaryFile updateCommentaryFile(CommentaryFile commentaryFile, String originalFileName, String storedFileName, String filePath){
        commentaryFile.originalFileName = originalFileName;
        commentaryFile.storedFileName = storedFileName;
        commentaryFile.filePath = filePath;
        return commentaryFile;
    }
}
