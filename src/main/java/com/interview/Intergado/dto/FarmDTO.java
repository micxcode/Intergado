package com.interview.Intergado.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmDTO {
    private Long id;
    @NonNull
    @ApiModelProperty(value = "The name of the Farm", required = true)
    private String name;
    @ApiModelProperty(value = "List of Animals that belong to the Farm")
    private List<AnimalDTO> animals;
}
