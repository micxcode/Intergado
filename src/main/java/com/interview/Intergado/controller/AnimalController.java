package com.interview.Intergado.controller;

import com.interview.Intergado.dto.AnimalDTO;
import com.interview.Intergado.resource.AnimalResource;
import com.interview.Intergado.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AnimalController implements AnimalResource {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(final AnimalService animalService) {
        this.animalService = animalService;
    }

    @Override
    public ResponseEntity animalList() {
        return animalService.animalList();
    }

    @Override
    public ResponseEntity add(final AnimalDTO animalDTO) {
        return animalService.add(animalDTO);
    }

    @Override
    public ResponseEntity addList(final List<AnimalDTO> animalDTOList) {
        return animalService.addList(animalDTOList);
    }

    @Override
    public ResponseEntity updateAnimal(final String id, final AnimalDTO animalDTO) {
        return animalService.updateAnimal(id, animalDTO);
    }

    @Override
    public ResponseEntity deleteAnimal(final String id) {
        return animalService.deleteAnimal(id);
    }
}
