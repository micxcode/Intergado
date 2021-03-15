package com.interview.Intergado.service;

import com.interview.Intergado.dto.FarmDTO;
import org.springframework.http.ResponseEntity;

public interface FarmService {

    ResponseEntity farmsList();

    ResponseEntity add(FarmDTO farmDTO);

    ResponseEntity updateFarm(String id, FarmDTO farmDTO);

    ResponseEntity deleteFarm(String id);
}
