package com.example.springbootprojectcinema.repository;

import com.example.springbootprojectcinema.model.entity.Place;
import com.example.springbootprojectcinema.model.entity.Room;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("from Room r where r.cinema.id =:id")
    List<Room> findAllById(Long id);

//    Room findAll(Class<Room> roomClass, int roomId);
}
