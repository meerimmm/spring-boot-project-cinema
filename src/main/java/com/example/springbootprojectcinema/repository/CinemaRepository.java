package com.example.springbootprojectcinema.repository;

import com.example.springbootprojectcinema.model.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long>{

}
