package com.example.springbootprojectcinema.model.entity;

import com.example.springbootprojectcinema.model.entity.Movie;
import com.example.springbootprojectcinema.model.entity.Room;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "sessions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    LocalDateTime start = LocalDateTime.now();
    int duration;
    LocalDateTime finish = LocalDateTime.now().plusHours(duration);
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    Movie movie;
    @Transient
    Long movieId;
    @ManyToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "session_and_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id")
    )
    List<Room> rooms;
    @Transient
    Long roomId;
    byte[] image;
}
