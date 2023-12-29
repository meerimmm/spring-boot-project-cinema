package com.example.springbootprojectcinema.service.impl;

import com.example.springbootprojectcinema.exception.EntityNotFoundException;
import com.example.springbootprojectcinema.model.entity.Movie;
import com.example.springbootprojectcinema.model.entity.Room;
import com.example.springbootprojectcinema.model.entity.Session;
import com.example.springbootprojectcinema.repository.MovieRepository;
import com.example.springbootprojectcinema.repository.RoomRepository;
import com.example.springbootprojectcinema.repository.SessionRepository;
import com.example.springbootprojectcinema.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SessionServiceImpl implements ServiceLayer<Session> {

    SessionRepository sessionRepository;
    MovieRepository movieRepository;
    RoomRepository roomRepository;

    @Override
    public void save(Session session) {
        Movie movie = movieRepository.findById(session.getMovieId()).orElseThrow(()->
                new EntityNotFoundException(String.format("Movie with id=%d not found", session.getMovieId())));
        session.setMovie(movie);
        sessionRepository.save(session);
    }

    @Override
    public Session findById(Long id) {
        return sessionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Session with id=%d not found!!!", id)));
    }

    @Override
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Session update(Long id, Session session) {

        Session session1 = findById(id);
        session1.setName(session.getName());
        session1.setStart(session.getStart());
        session1.setFinish(session.getFinish());
        session1.setDuration(session.getDuration());
        session1.setRooms(session.getRooms());
        session1.setImage(session.getImage());

        sessionRepository.save(session1);
        return session1;
    }

    public List<Session> findAllId(Long id) {
        return sessionRepository.findAllById(id);
    }

    @Override
    public void deleteById(Long id) {
        sessionRepository.deleteById(id);
    }
}
