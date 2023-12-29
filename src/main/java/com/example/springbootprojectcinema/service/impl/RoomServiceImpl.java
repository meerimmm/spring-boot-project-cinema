package com.example.springbootprojectcinema.service.impl;

import com.example.springbootprojectcinema.exception.EntityNotFoundException;
import com.example.springbootprojectcinema.model.entity.Cinema;
import com.example.springbootprojectcinema.model.entity.Room;
import com.example.springbootprojectcinema.repository.CinemaRepository;
import com.example.springbootprojectcinema.repository.RoomRepository;
import com.example.springbootprojectcinema.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoomServiceImpl implements ServiceLayer<Room> {

    RoomRepository roomRepository;
    CinemaRepository cinemaRepository;

    @Override
    public void save(Room room) {
        Cinema cinema = cinemaRepository.findById(room.getCinemaId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Cinema with id=%d not found!!!", room.getCinemaId())));
        room.setCinema(cinema);
        roomRepository.save(room);
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Room with id=%d not found!!!", id)));
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room update(Long id, Room room) {

        Room oldRoom = findById(id);
        oldRoom.setName(room.getName());
        oldRoom.setImage(room.getImage());
        oldRoom.setCinema(room.getCinema());
        oldRoom.setSessions(room.getSessions());
        oldRoom.setRating(room.getRating());
        oldRoom.setPlaces(room.getPlaces());

        roomRepository.save(oldRoom);
        return oldRoom;
    }

    public List<Room> findAllId(Long id) {
        return roomRepository.findAllById(id);
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }
}
