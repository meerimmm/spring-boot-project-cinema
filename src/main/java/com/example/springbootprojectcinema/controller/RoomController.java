package com.example.springbootprojectcinema.controller;

import com.example.springbootprojectcinema.model.entity.Cinema;
import com.example.springbootprojectcinema.model.entity.Movie;
import com.example.springbootprojectcinema.model.entity.Room;
import com.example.springbootprojectcinema.service.impl.CinemaServiceImpl;
import com.example.springbootprojectcinema.service.impl.RoomServiceImpl;
import com.example.springbootprojectcinema.service.impl.SessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/room")
@PreAuthorize("hasAuthority('ADMIN')")
public class RoomController {
    private final RoomServiceImpl roomService;
    private final CinemaServiceImpl cinemaService;
    private final SessionServiceImpl sessionService;

    @Autowired
    public RoomController(RoomServiceImpl roomService, CinemaServiceImpl cinemaService, SessionServiceImpl sessionService) {
        this.roomService = roomService;
        this.cinemaService = cinemaService;
        this.sessionService = sessionService;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @ModelAttribute("cinemaList")
    public List<Cinema> cinemaList() {
        return cinemaService.findAll();
    }

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("room", new Room());
        return "roomHtml/room";

    }

    @PostMapping("/save_room")
    public String saveCinema(@ModelAttribute Room room, @RequestParam("file") MultipartFile multipartFile) throws IOException {
       room .setImage(multipartFile.getBytes());
        roomService.save(room);
        return "redirect:/room/find_all";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")

    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_rooms", roomService.findAll());
        return "roomHtml/all_rooms";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/findAllId/{id}")
    public String findAllId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("all_room_id", roomService.findAllId(id));
        return "roomHtml/all_room_id";
    }


    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id)  {
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        return "roomHtml/update_room";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Room room, @PathVariable Long id,  @RequestParam("file") MultipartFile multipartFile) throws IOException  {
       room.setImage(multipartFile.getBytes());
        roomService.update(id, room);
        return "redirect:/room/find_all";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/find_by_id/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("find_by_id", sessionService.findById(id));
        return "roomHtml/find_by_id";
    }

    @GetMapping("/delete_by_id/{id}")
    public String deleteById(@PathVariable Long id) {
        roomService.deleteById(id);
        return "redirect:/room/find_all";
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Room room = roomService.findById(id);
        if (room != null && room.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(room.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

