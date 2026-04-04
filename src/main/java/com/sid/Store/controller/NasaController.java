package com.sid.Store.controller;

import com.sid.Store.Service.NasaService;
import com.sid.Store.dto.NasaDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NasaController {

    private final NasaService nasaService;

    public NasaController(NasaService nasaService) {
        this.nasaService = nasaService;
    }

    @GetMapping("/api/apod")
    public NasaDto getApod() {
        return nasaService.getTodayApod();
    }
}