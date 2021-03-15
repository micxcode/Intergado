package com.interview.Intergado.service;

import com.interview.Intergado.dto.AnimalDTO;
import com.interview.Intergado.dto.FarmDTO;
import com.interview.Intergado.repository.FarmRepository;
import com.interview.Intergado.repository.domain.Animal;
import com.interview.Intergado.repository.domain.Farm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FarmServiceImpl implements FarmService{

    private final FarmRepository farmRepository;

    @Autowired
    public FarmServiceImpl(final FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @Override
    public ResponseEntity farmsList() {
        List<Farm> farmList = (List<Farm>) farmRepository.findAll();
        return new ResponseEntity(mapListToDTO(farmList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity add(final FarmDTO farmDTO) {
        log.info("Registering new farm with name={}", farmDTO.getName());

        log.debug("Looking for existing Farm with name={}", farmDTO.getName());
        Farm existingFarm = farmRepository.findByName(farmDTO.getName());
        if(existingFarm != null){
            log.warn("Farm with name={} already exists.", farmDTO.getName());
            return new ResponseEntity("Farm already registered with the name provided.", HttpStatus.BAD_REQUEST);
        }
        log.debug("No existing Farm found.");

        try {
            Farm farm = new Farm(farmDTO.getName());
            log.debug("Saving farm={}", farm);
            farmRepository.save(farm);
        } catch (Exception e){
            log.error("Error registering new farm with name={}", farmDTO.getName(), e);
            return new ResponseEntity("Failed to register new farm.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Farm={} registered successfully ", farmDTO.getName());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateFarm(final String id, final FarmDTO farmDTO) {
        log.info("Updating Farm with id={}", id);

        Farm farm = parseAndGetFarmById(id);

        if(farm == null){
            log.warn("Couldn't find Farm with id={}", id);
            return new ResponseEntity("Farm not found.", HttpStatus.BAD_REQUEST);
        }
        log.debug("Found Farm with id={}", farm.getId());

        try {
            log.debug("Updating Farm with new data={}", farmDTO);
            farmRepository.updateFarm(farmDTO.getName(), farm.getId());
        } catch (Exception e){
            log.error("Error when updating Farm with id={}", id, e);
            return new ResponseEntity("Failed to update farm.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Farm updated for id={}", id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity deleteFarm(final String id) {
        log.info("Deleting farm with id={}", id);

        Farm farm = parseAndGetFarmById(id);

        if(farm == null){
            log.warn("Couldn't find Farm with id={}", id);
            return new ResponseEntity("Farm not found.", HttpStatus.BAD_REQUEST);
        }
        log.debug("Farm found with id={}", farm.getId());

        try {
            //Attention, it will delete all animals related
            log.debug("Deleting Farm={}", farm);
            farmRepository.delete(farm);
        } catch (Exception e){
            log.error("Error deleting farm with id={}", farm.getId(), e);
            return new ResponseEntity("Failed to delete farm.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Farm with id={} deleted successfully.", id);
        return new ResponseEntity(HttpStatus.OK);
    }

    private Farm parseAndGetFarmById(final String id){
        Long farmId;

        try{
            log.debug("Parsing id={}", id);
            farmId = Long.parseLong(id);
        }
        catch (NumberFormatException e){
            log.warn("Couldn't parse id={}", id);
            return null;
        }

        Optional<Farm> farm = farmRepository.findById(farmId);

        return farm.isEmpty() ? null : farm.get();
    }

    //Perhaps it would be interesting to use BoundMapperFacade instead of this conventional map
    private List<FarmDTO> mapListToDTO(List<Farm> farmList){
        List<FarmDTO> farmDTOList = new java.util.ArrayList<>();
        for (Farm farm: farmList) {
            List<AnimalDTO> animalDTOList = new java.util.ArrayList<>();
            for (Animal animal: farm.getAnimals()) {
                AnimalDTO animalDTO = new AnimalDTO(animal.getId(), animal.getTag(), farm.getName());
                animalDTOList.add(animalDTO);
            }
            FarmDTO farmDTO = new FarmDTO(farm.getId(), farm.getName(), animalDTOList);
            farmDTOList.add(farmDTO);
        }
        return farmDTOList;
    }
}
