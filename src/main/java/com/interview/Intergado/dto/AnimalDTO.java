package com.interview.Intergado.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {
    private Long id;
    @NonNull
    @ApiModelProperty(value = "Identification of the Animal", required = true)
    private String tag;
    @NonNull
    @ApiModelProperty(value = "The Farm name from where the Animal belong", required = true)
    private String farmName;
}
