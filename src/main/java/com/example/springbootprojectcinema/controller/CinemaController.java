package com.example.springbootprojectcinema.controller;

import com.example.springbootprojectcinema.model.entity.Cinema;
import com.example.springbootprojectcinema.service.impl.CinemaServiceImpl;
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

@Controller
@RequestMapping("/cinema")
@PreAuthorize("hasAuthority('ADMIN')")
public class CinemaController {
    private final CinemaServiceImpl cinemaService;

    @Autowired
    public CinemaController(CinemaServiceImpl cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/save")
    public String save(Model model) {
        Cinema cinema = new Cinema();
        model.addAttribute("cinema", cinema);
        return "cinemaHtml/cinema";
    }

    @PostMapping("/save_cinema")
    public String saveCinema(@ModelAttribute Cinema cinema, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        cinema.setImage(multipartFile.getBytes());
        cinemaService.save(cinema);
        return "redirect:/cinema/find_all";
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_cinemas", cinemaService.findAll());
        return "cinemaHtml/all_cinemas";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/find_by_id/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("only_one_user", cinemaService.findById(id));
        return "cinemaHtml/find_by_id";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id)  {
        Cinema cinema = cinemaService.findById(id);
        System.out.println("Java");
        model.addAttribute("cinema", cinema);
        return "cinemaHtml/update_cinema";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Cinema cinema, @PathVariable Long id , @RequestParam("file") MultipartFile multipartFile) throws IOException  {
       cinema.setImage(multipartFile.getBytes());
        System.out.println("Hello");
        System.out.println(cinema.getName());
        System.out.println("J");
        cinemaService.update(id, cinema);
        return "redirect:/cinema/find_all";
    }

    @GetMapping("/delete_by_id/{id}")
    public String delete(@PathVariable Long id) {
        cinemaService.deleteById(id);
        return "redirect:/cinema/find_all";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Cinema cinema = cinemaService.findById(id);
        if (cinema != null && cinema.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(cinema.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}

