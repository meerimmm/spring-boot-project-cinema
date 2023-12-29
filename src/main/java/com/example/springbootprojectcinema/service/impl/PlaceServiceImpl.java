package com.example.springbootprojectcinema.service.impl;

import com.example.springbootprojectcinema.exception.EntityNotFoundException;
import com.example.springbootprojectcinema.model.entity.Place;
import com.example.springbootprojectcinema.model.entity.Room;
import com.example.springbootprojectcinema.repository.PlaceRepository;
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
public class PlaceServiceImpl implements ServiceLayer<Place> {

    PlaceRepository placeRepository;

    RoomRepository roomRepository;

    @Override
    public void save(Place place) {
        Room room = roomRepository.findById(place.getRoomId()).get();
        place.setRoom(room);
        placeRepository.save(place);

    }

    @Override
    public Place findById(Long id) {
        return placeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Place with id=%d not found!!!", id)));
    }

    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    public Place update(Long id, Place place) {

        Place oldPlace = findById(id);
        oldPlace.setPlace(place.getPlace());
        oldPlace.setPrice(place.getPrice());
        oldPlace.setRow(place.getRow());
        oldPlace.setRoom(place.getRoom());

        placeRepository.save(oldPlace);
        return oldPlace;
    }

    public List<Place> findAll(Long id) {
        return placeRepository.findAllById(id);
    }

    @Override
    public void deleteById(Long id) {
        placeRepository.deleteById(id);
    }
}
