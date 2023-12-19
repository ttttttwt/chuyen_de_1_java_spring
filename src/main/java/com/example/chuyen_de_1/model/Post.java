package com.example.chuyen_de_1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

@Entity
@Table(name = "tb_post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long categoryId;
    private String title;
    private String content;

    @CreationTimestamp
    private Date createdDate;

    @Column(columnDefinition = "int default 0")
    private int likes;

}
