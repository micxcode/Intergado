package com.interview.Intergado.service;

import com.interview.Intergado.dto.AnimalDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnimalService {

    ResponseEntity animalList();

    ResponseEntity add(AnimalDTO animalDTO);

    ResponseEntity addList(List<AnimalDTO> animalDTOList);

    ResponseEntity updateAnimal(String id, AnimalDTO animalDTO);

    ResponseEntity deleteAnimal(String id);
}
