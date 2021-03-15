package com.interview.Intergado.resource;

import com.interview.Intergado.dto.AnimalDTO;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/intergado/animal")
public interface AnimalResource {

    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity animalList();

    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity add(@Validated @RequestBody AnimalDTO animalDTO);

    @PostMapping(value = "/addList",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity addList(@Validated @RequestBody List<AnimalDTO> animalDTOList);

    @PutMapping(value = "/{id}/update-animal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity updateAnimal(@NonNull @PathVariable(name = "id") final String id,
                                  @RequestBody AnimalDTO animalDTO);

    @DeleteMapping(value = "/{id}/delete-animal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteAnimal(@NonNull @PathVariable(name = "id") final String id);
}
