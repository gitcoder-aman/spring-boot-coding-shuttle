package com.tech.module5.SecurityApplication.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

//    @NotAudited
    private String description;

    @ManyToOne
    private UserApp author;

    @PrePersist
    void beforeSave(){

    }

    @PreUpdate
    void beforeUpdate(){

    }

    @PreRemove
    void beforeDelete(){

    }

}
