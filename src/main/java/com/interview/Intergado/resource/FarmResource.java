package com.interview.Intergado.resource;

import com.interview.Intergado.dto.FarmDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(value = "Farm API")
@RequestMapping(value = "/api/intergado/farm")
public interface FarmResource {

    @ApiOperation(value = "Return list of existing Farms")
    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity farmsList();

    @ApiOperation(value = "Add a new Farm")
    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity add(@Validated @RequestBody FarmDTO farmDTO);

    @ApiOperation(value = "Update an existing Farm")
    @PutMapping(value = "/{id}/update-farm",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity updateFarm(@PathVariable(name = "id") final String id,
                                @RequestBody FarmDTO farmDTO);

    @ApiOperation(value = "Delete an existing Farm")
    @DeleteMapping(value = "/{id}/delete-farm",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteFarm(@PathVariable(name = "id") final String id);
}
