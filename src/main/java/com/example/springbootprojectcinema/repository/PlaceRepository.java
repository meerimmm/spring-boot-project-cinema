package com.example.springbootprojectcinema.repository;

import com.example.springbootprojectcinema.model.entity.Place;
import com.example.springbootprojectcinema.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query("from Place r where r.room.id =:id")
    List<Place> findAllById(Long id);

}
