package com.interview.Intergado.controller;

import com.interview.Intergado.dto.FarmDTO;
import com.interview.Intergado.resource.FarmResource;
import com.interview.Intergado.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class FarmController implements FarmResource {

    private final FarmService farmService;

    @Autowired
    public FarmController(final FarmService farmService) {
        this.farmService = farmService;
    }

    @Override
    public ResponseEntity farmsList() {
        return farmService.farmsList();
    }

    @Override
    public ResponseEntity add(final FarmDTO farmDTO) {
        return farmService.add(farmDTO);
    }

    @Override
    public ResponseEntity updateFarm(final String id, final FarmDTO farmDTO) {
        return farmService.updateFarm(id, farmDTO);
    }

    @Override
    public ResponseEntity deleteFarm(final String id) {
        return farmService.deleteFarm(id);
    }
}
