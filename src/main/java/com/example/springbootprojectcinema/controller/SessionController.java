package com.example.springbootprojectcinema.controller;

import com.example.springbootprojectcinema.model.entity.Cinema;
import com.example.springbootprojectcinema.model.entity.Movie;
import com.example.springbootprojectcinema.model.entity.Room;
import com.example.springbootprojectcinema.model.entity.Session;
import com.example.springbootprojectcinema.service.impl.MovieServiceImpl;
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
@RequestMapping("/session")
public class SessionController {
    private final SessionServiceImpl sessionService;
    private final RoomServiceImpl roomService;
    private final MovieServiceImpl movieService;

    @Autowired
    public SessionController(SessionServiceImpl sessionService, MovieServiceImpl movieService, RoomServiceImpl roomService, MovieServiceImpl movieService1) {
        this.sessionService = sessionService;
        this.roomService = roomService;
        this.movieService = movieService1;
    }

    @ModelAttribute("roomList")
    public List<Room> roomList() {
        return roomService.findAll();
    }

    @ModelAttribute("movieList")
    public List<Movie> movieList() {
        return movieService.findAll();
    }

    @GetMapping("/save")
    public String save(Model model) {
        Session session = new Session();

        model.addAttribute("ses", session);
//            model.addAttribute("room1",roomService.findAll());
        return "sessionHtml/session";
    }

    @PostMapping("/save_session")
    public String saveCinema(@ModelAttribute Session session, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        session.setImage(multipartFile.getBytes());
        sessionService.save(session);
        return "redirect:/session/find_all";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")

    @GetMapping("/find_all")
    public String findAll(Model model) {
//            System.out.println("Hello");
//            for (Session session : sessionService.findAll()) {
//            System.out.println(session.getName());
//                System.out.println(session.getMovie().getName());
//        }
        model.addAttribute("all_sessions", sessionService.findAll());
        return "sessionHtml/all_sessions";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")

    @GetMapping("/findAllId/{id}")
    public String findAllId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("all_session_id", sessionService.findAllId(id));
        return "sessionHtml/all_session_id";
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")

    @GetMapping("/find_by_id/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("find_by_id", sessionService.findById(id));
        return "sessionHtml/find_by_id";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id)  {
        Session session = sessionService.findById(id);
        model.addAttribute("ses", session);
        return "sessionHtml/update_session";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Session session, @PathVariable Long id , @RequestParam ("file") MultipartFile multipartFile) throws IOException {
       session.setImage(multipartFile.getBytes());
        sessionService.update(id, session);
        return "redirect:/session/find_all";
    }


    @GetMapping("/delete_by_id/{id}")
    public String deleteById(@PathVariable Long id) {
        sessionService.deleteById(id);
        return "redirect:/session/find_all";
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Session session = sessionService.findById(id);
        if (session != null && session.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(session.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
