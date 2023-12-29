package com.example.springbootprojectcinema.model.entity;

import com.example.springbootprojectcinema.model.enums.Genres;
import com.example.springbootprojectcinema.model.enums.Languages;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "movies")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;
    String name;
    @Enumerated(EnumType.STRING)
    Genres genres;
    @CreatedDate
    @Column(name = "created_date")
    LocalDate createdDate;
    String country;
    @Enumerated(EnumType.STRING)
    Languages languages;
    byte[] image;
    @OneToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.REMOVE}, mappedBy = "movie")
    List<Session> sessions;

}

