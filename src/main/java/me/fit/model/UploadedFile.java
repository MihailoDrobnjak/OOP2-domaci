package me.fit.model;

import jakarta.persistence.*;
import java.io.File;

@Entity
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "upload_seq")
    @SequenceGenerator(name = "upload_seq", sequenceName = "upload_seq", allocationSize = 1)
    public Long id;

    public String filename;

    @Transient
    public File file;
}