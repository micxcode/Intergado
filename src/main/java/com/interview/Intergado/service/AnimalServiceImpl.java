package com.interview.Intergado.service;

import com.interview.Intergado.dto.AnimalDTO;
import com.interview.Intergado.repository.AnimalRepository;
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
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final FarmRepository farmRepository;
    private static final String LOOKING_FOR_FARM_WITH_NAME = "Looking for Farm with name={}";

    @Autowired
    public AnimalServiceImpl(final AnimalRepository animalRepository,
                             final FarmRepository farmRepository) {
        this.animalRepository = animalRepository;
        this.farmRepository = farmRepository;
    }

    @Override
    public ResponseEntity animalList() {
        List<Animal> animalList = (List<Animal>) animalRepository.findAll();
        return new ResponseEntity(mapListToDTO(animalList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity add(final AnimalDTO animalDTO) {
        log.info("Registering new animal with tag={}", animalDTO.getTag());

        log.debug(LOOKING_FOR_FARM_WITH_NAME, animalDTO.getFarmName());
        Farm farm = farmRepository.findByName(animalDTO.getFarmName());

        //Should save the farm if not in DB yet? Idk
        if(farm == null){
            log.warn("Farm not found with name={}", animalDTO.getFarmName());
            return new ResponseEntity("Farm not found.", HttpStatus.BAD_REQUEST);
        }
        log.debug("Found Farm with id={}", farm.getId());

        try {
            Animal animal = new Animal(animalDTO.getTag(), farm);
            log.debug("Saving animal={}", animal);
            animalRepository.save(animal);
            farm.getAnimals().add(animal);
            log.debug("Updating farm={}", farm);
            farmRepository.save(farm);
        } catch (Exception e){
            log.error("Error saving new animal with tag={}", animalDTO.getTag(), e);
            return new ResponseEntity("Failed to register new animal.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Animal registered successfully with tag={}", animalDTO.getTag());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity addList(final List<AnimalDTO> animalDTOList) {
        if(log.isDebugEnabled()){
            log.debug("Receiving animalList={}", animalDTOList);
        } else {
            log.info("Receiving list of animals.");
        }

        List<Animal> animalList = new java.util.ArrayList<>();
        for (AnimalDTO animalDTO: animalDTOList) {
            log.debug(LOOKING_FOR_FARM_WITH_NAME, animalDTO.getFarmName());
            Farm farm = farmRepository.findByName(animalDTO.getFarmName());
            //Should it save a new farm as well?
            //This is not specified on the project description
            if(farm == null){
                log.warn("Farm not found with name={}", animalDTO.getFarmName());
                return new ResponseEntity(String.format("Farm not found with name=%s", animalDTO.getFarmName()),
                        HttpStatus.BAD_REQUEST);
            }
            log.debug("Found Farm with id={}", farm.getId());

            Animal animal = new Animal(animalDTO.getTag(), farm);

            if(log.isDebugEnabled()){
                log.debug("Adding animal={}", animal);
            } else {
                log.info("Adding animal with id={}", animal.getId());
            }

            animalList.add(animal);
        }

        try {
            log.debug("Saving animalList={}", animalList);
            animalRepository.saveAll(animalList);

        } catch (Exception e){
            log.error("Error saving list of animals={}", animalList, e);
            return new ResponseEntity("Failed to register animal list.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Animal list registered successfully.");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateAnimal(final String id, final AnimalDTO animalDTO) {
        log.info("Updating animal with id={}", id);

        Animal animal = parseAndGetAnimalById(id);

        if(animal == null){
            log.warn("Couldn't find Animal with id={}", id);
            return new ResponseEntity("Animal not found.", HttpStatus.BAD_REQUEST);
        }
        log.debug("Animal found with id={}", animal.getId());

        try{
            log.debug(LOOKING_FOR_FARM_WITH_NAME, animalDTO.getFarmName());
            Farm farm = farmRepository.findByName(animalDTO.getFarmName());
            if(farm == null){
                log.warn("Couldn't find Farm with name={}", animalDTO.getFarmName());
                return new ResponseEntity("Farm not found.", HttpStatus.BAD_REQUEST);
            }
            log.debug("Farm found with id={}", farm.getId());
            log.debug("Updating Animal with new data={}", animalDTO);
            animalRepository.updateAnimal(animalDTO.getTag(), animal.getId(), farm.getId());
        } catch (Exception e){
            log.error("Error when updating animal with id={}", animal.getId(), e);
            return new ResponseEntity("Failed to update animal.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Animal updated for id={}", id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity deleteAnimal(final String id) {
        log.info("Deleting animal with id={}", id);

        Animal animal = parseAndGetAnimalById(id);
        if(animal == null){
            log.warn("Couldn't find Animal with id={}", id);
            return new ResponseEntity("Animal not found.", HttpStatus.BAD_REQUEST);
        }
        log.debug("Found animal with id={}", animal.getId());

        try {
            log.debug("Deleting animal={}", animal);
            animalRepository.delete(animal);
        } catch (Exception e){
            log.error("Error deleting animal with id={}", animal.getId(), e);
            return new ResponseEntity("Failed to delete animal.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Animal with id={} deleted successfully.", id);
        return new ResponseEntity(HttpStatus.OK);
    }

    private Animal parseAndGetAnimalById(final String id){
        Long animalId;

        try{
            log.debug("Parsing id={}", id);
            animalId = Long.parseLong(id);
        }
        catch (NumberFormatException e){
            log.warn("Couldn't parse id={}", id);
            return null;
        }

        Optional<Animal> animal = animalRepository.findById(animalId);

        return animal.orElse(null);
    }

    //Perhaps it would be interesting to use BoundMapperFacade instead of this conventional map
    private List<AnimalDTO> mapListToDTO(List<Animal> animalList){
        List<AnimalDTO> animalDTOList = new java.util.ArrayList<>();
        for (Animal animal : animalList) {
            AnimalDTO animalDTO = new AnimalDTO(animal.getId(), animal.getTag(), animal.getFarm().getName());
            animalDTOList.add(animalDTO);
        }
        return animalDTOList;
    }
}
