package com.example.springbootprojectcinema.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "places")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int row;
    int place;
    int price;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    Room room;
    @Transient
    Long roomId;


}
