package com.example.springbootprojectcinema.service.impl;

import com.example.springbootprojectcinema.exception.EntityNotFoundException;
import com.example.springbootprojectcinema.model.entity.Cinema;
import com.example.springbootprojectcinema.repository.CinemaRepository;
import com.example.springbootprojectcinema.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CinemaServiceImpl implements ServiceLayer<Cinema> {

    private  final CinemaRepository cinemaRepository;

    @Override
    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    @Override
    public Cinema findById(Long id) {
        return cinemaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Cinema with id=%d not found!!!", id)));
    }

    @Override
    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    @Override
    public Cinema update(Long id, Cinema cinema) {
        Cinema oldCinema = findById(id);
        oldCinema.setName(cinema.getName());
        oldCinema.setAddress(cinema.getAddress());
        oldCinema.setImage(cinema.getImage());
        System.out.println(oldCinema.getName());
        cinemaRepository.save(oldCinema);
        return oldCinema;
    }
    @Override
    public void deleteById(Long id) {
        cinemaRepository.deleteById(id);
    }
}
