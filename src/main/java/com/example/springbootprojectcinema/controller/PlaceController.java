package com.example.springbootprojectcinema.controller;

import com.example.springbootprojectcinema.model.entity.Place;
import com.example.springbootprojectcinema.model.entity.Room;
import com.example.springbootprojectcinema.service.impl.PlaceServiceImpl;
import com.example.springbootprojectcinema.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/place")
public class PlaceController {
    private final PlaceServiceImpl placeService;
    private final RoomServiceImpl roomService;

    @Autowired
    public PlaceController(PlaceServiceImpl placeService, RoomServiceImpl roomService) {
        this.placeService = placeService;
        this.roomService = roomService;
    }

    @ModelAttribute("roomList")
    public List<Room> roomList() {
        return roomService.findAll();
    }

    @GetMapping("/save")
    public String save(Model model) {
        Place place = new Place();
        List<Room> rooms = roomService.findAll();
        model.addAttribute("place", place);
        model.addAttribute("room1", rooms);
        return "placeHtml/place";
    }

    @PostMapping("/save_place")
    public String saveUser(@ModelAttribute Place place) {
        placeService.save(place);
        return "redirect:find_all";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")

    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_places", placeService.findAll());
        return "placeHtml/all_places";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/findAllId/{id}")
    public String findAllId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("all_place_id", roomService.findAllId(id));
        return "placeHtml/all_place_id";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/find_by_id/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("find_by_id", id);
        return "placeHtml/find_by_id";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        Place place = placeService.findById(id);
        model.addAttribute("place", place);
        return "placeHtml/update_place";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Place place, @PathVariable Long id) {
        placeService.update(id, place);
        return "redirect:/place/find_all";
    }

    @GetMapping("/delete_by_id/{id}")
    public String deleteById(@PathVariable Long id) {
        placeService.deleteById(id);
        return "redirect:/place/find_all";
    }

}

