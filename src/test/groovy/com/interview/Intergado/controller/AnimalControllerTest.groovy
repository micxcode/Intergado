package com.interview.Intergado.controller

import com.interview.Intergado.dto.AnimalDTO
import com.interview.Intergado.service.AnimalService
import spock.lang.Specification

class AnimalControllerTest extends Specification{
    def animalService = Mock(AnimalService)
    def animalDTO = Mock(AnimalDTO)
    def animalController = new AnimalController(animalService)

    def "add animal"() {
        when:
        animalController.add(animalDTO)
        then:
        1 * animalService.add(animalDTO)
    }

    def "add animal list"() {
        when:
        animalController.addList([animalDTO])
        then:
        1 * animalService.addList([animalDTO])
    }

    def "update animal"() {
        when:
        animalController.updateAnimal("animal_id", animalDTO)
        then:
        1 * animalService.updateAnimal("animal_id", animalDTO)
    }

    def "animal list"() {
        when:
        animalController.animalList()
        then:
        1 * animalService.animalList()
    }

    def "delete animal"() {
        when:
        animalController.deleteAnimal("animal_id")
        then:
        1 * animalService.deleteAnimal("animal_id")
    }
}
