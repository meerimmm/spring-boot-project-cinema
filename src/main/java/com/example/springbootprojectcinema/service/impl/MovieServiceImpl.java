package com.example.springbootprojectcinema.service.impl;

import com.example.springbootprojectcinema.exception.EntityNotFoundException;
import com.example.springbootprojectcinema.model.entity.Movie;
import com.example.springbootprojectcinema.repository.MovieRepository;
import com.example.springbootprojectcinema.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MovieServiceImpl implements ServiceLayer<Movie> {

   private final MovieRepository movieRepository;

    @Override
    public void save(Movie movie) {
        movie.setCreatedDate(LocalDate.now());
        movieRepository.save(movie);
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Movie with id=%d not found!!!", id)));
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie update(Long id, Movie movie) {

        Movie oldMovie = findById(id);
        oldMovie.setName(movie.getName());
        oldMovie.setCountry(movie.getCountry());
        oldMovie.setImage(movie.getImage());
        oldMovie.setLanguages(movie.getLanguages());
        oldMovie.setCreatedDate(LocalDate.now());
        oldMovie.setGenres(movie.getGenres());
        oldMovie.setSessions(movie.getSessions());
        movieRepository.save(oldMovie);
        return oldMovie;
    }

    public List<Movie> findAllId(Long id) {
        return movieRepository.findAllById(id);
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }
}
