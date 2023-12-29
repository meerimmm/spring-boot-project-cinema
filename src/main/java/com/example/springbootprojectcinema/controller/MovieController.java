package com.example.springbootprojectcinema.controller;

import com.example.springbootprojectcinema.model.entity.Movie;
import com.example.springbootprojectcinema.model.enums.Genres;
import com.example.springbootprojectcinema.model.enums.Languages;
import com.example.springbootprojectcinema.service.impl.MovieServiceImpl;
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
@RequestMapping("/movie")
public class MovieController {
    private final MovieServiceImpl movieService;

    @Autowired
    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @ModelAttribute("all_genres")
    public List<Genres> findALlGenres(){
        return List.of(Genres.values());
    }

    @ModelAttribute("all_languages")
    public List<Languages> findAllLanguages(){
        return List.of(Languages.values());
    }

    @GetMapping("/save")
    public String save(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        return "movieHtml/movie";
    }

    @PostMapping("/save_movie")
    public String saveCinema(@ModelAttribute Movie movie, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        movie.setImage(multipartFile.getBytes());
        movieService.save(movie);
        return "redirect:/movie/find_all";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")

    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_movies", movieService.findAll());
        return "movieHtml/all_movies";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")

    @GetMapping("/findAllId/{id}")
    public String findAllId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("all_movies_id", movieService.findAllId(id));
        return "movieHtml/all_movie_id";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/find_by_id/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("find_by_id", movieService.findById(id));
        return "movieHtml/find_by_id";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id ) {
        Movie movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        return "movieHtml/update_movie";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Movie movie, @PathVariable Long id,@RequestParam("file") MultipartFile multipartFile) throws IOException {
       movie.setImage(multipartFile.getBytes());
        movieService.update(id, movie);
        return "redirect:/movie/find_all";
    }

    @GetMapping("/delete_by_id/{id}")
    public String deleteById(@PathVariable Long id) {
        movieService.deleteById(id);
        return "redirect:/movie/find_all";
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Movie movie = movieService.findById(id);
        if (movie !=null && movie.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(movie.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}