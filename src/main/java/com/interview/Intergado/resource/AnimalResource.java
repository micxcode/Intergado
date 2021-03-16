package com.interview.Intergado.resource;

import com.interview.Intergado.dto.AnimalDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(value = "Animal API")
@RequestMapping(value = "/api/intergado/animal")
public interface AnimalResource {

    @ApiOperation("Return list of existing Animals")
    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity animalList();

    @ApiOperation("Add a new Animal")
    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity add(@Validated @RequestBody AnimalDTO animalDTO);

    @ApiOperation("Add a list of new Animals")
    @PostMapping(value = "/addList",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity addList(@Validated @RequestBody List<AnimalDTO> animalDTOList);

    @ApiOperation("Update an existing Animal")
    @PutMapping(value = "/{id}/update-animal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity updateAnimal(@NonNull @PathVariable(name = "id") final String id,
                                  @RequestBody AnimalDTO animalDTO);

    @ApiOperation("Delete an existing Animal")
    @DeleteMapping(value = "/{id}/delete-animal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteAnimal(@NonNull @PathVariable(name = "id") final String id);
}
