package com.interview.Intergado.service

import com.interview.Intergado.dto.AnimalDTO
import com.interview.Intergado.repository.AnimalRepository
import com.interview.Intergado.repository.FarmRepository
import com.interview.Intergado.repository.domain.Animal
import com.interview.Intergado.repository.domain.Farm
import org.springframework.http.HttpStatus
import spock.lang.Specification

class AnimalServiceImplTest extends Specification{
    def farmRepository = Mock(FarmRepository)
    def animalRepository = Mock(AnimalRepository)
    def animalServiceImpl = new AnimalServiceImpl(animalRepository, farmRepository)
    def animalDTO = new AnimalDTO(1, "tag", "farm")

    def "animalList() - Return empty"() {
        given:
        animalRepository.findAll() >> []

        when:
        def response = animalServiceImpl.animalList()

        then:
        response.statusCode == HttpStatus.OK
    }

    def "animalList() - Return list"() {
        given:
        def animal = new Animal()
        animal.id = animalDTO.getId()
        animal.tag = animalDTO.getTag()
        def farm = new Farm()
        farm.name = animalDTO.getFarmName()
        animal.farm = farm
        animalRepository.findAll() >> [animal]

        when:
        def response = animalServiceImpl.animalList()

        then:
        def responseData = response.body as List<AnimalDTO>
        responseData[0].id == animalDTO.getId()
        responseData[0].tag == animalDTO.getTag()
        responseData[0].farmName == animalDTO.getFarmName()
        response.statusCode == HttpStatus.OK
    }

    def "add() - Farm not found"() {
        given:
        farmRepository.findByName(*_) >> null

        when:
        def response = animalServiceImpl.add(animalDTO)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Farm not found."
    }

    def "add() - Failed to register"() {
        given:
        farmRepository.findByName(*_) >> new Farm()
        animalRepository.save(*_) >> {throw (Exception)}

        when:
        def response = animalServiceImpl.add(animalDTO)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body == "Failed to register new animal."
    }

    def "add() - Success"() {
        given:
        farmRepository.findByName(*_) >> new Farm()

        when:
        def response = animalServiceImpl.add(animalDTO)

        then:
        response.statusCode == HttpStatus.CREATED
    }

    def "addList() - Farm not found"() {
        given:
        farmRepository.findByName(*_) >> null

        when:
        def response = animalServiceImpl.addList([animalDTO])

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Farm not found with name=" + animalDTO.getFarmName()
    }

    def "addList() - Failed to register list"() {
        given:
        farmRepository.findByName(*_) >> new Farm()
        animalRepository.saveAll(*_) >> {throw (Exception)}

        when:
        def response = animalServiceImpl.addList([animalDTO])

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body == "Failed to register animal list."
    }

    def "addList() - Success"() {
        given:
        farmRepository.findByName(*_) >> new Farm()

        when:
        def response = animalServiceImpl.addList([animalDTO])

        then:
        response.statusCode == HttpStatus.CREATED
    }

    def "updateAnimal() - Animal not found"() {
        given:
        animalRepository.findById(*_) >> Optional.ofNullable(null)

        when:
        def response = animalServiceImpl.updateAnimal("1", animalDTO)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Animal not found."
    }

    def "updateAnimal() - Farm not found"() {
        given:
        animalRepository.findById(*_) >> Optional.ofNullable(new Animal())
        farmRepository.findByName(*_) >> null

        when:
        def response = animalServiceImpl.updateAnimal("1", animalDTO)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Farm not found."
    }

    def "updateAnimal() - Failed to update"() {
        given:
        animalRepository.findById(*_) >> Optional.ofNullable(new Animal())
        farmRepository.findByName(*_) >> new Farm()
        animalRepository.updateAnimal(*_, *_, *_) >> {throw (Exception)}

        when:
        def response = animalServiceImpl.updateAnimal("1", animalDTO)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body == "Failed to update animal."
    }

    def "updateAnimal() - Success"() {
        given:
        animalRepository.findById(*_) >> Optional.ofNullable(new Animal())
        farmRepository.findByName(*_) >> new Farm()

        when:
        def response = animalServiceImpl.updateAnimal("1", animalDTO)

        then:
        response.statusCode == HttpStatus.NO_CONTENT
    }

    def "deleteAnimal() - Animal not found"() {
        given:
        animalRepository.findById(*_) >> Optional.ofNullable(null)

        when:
        def response = animalServiceImpl.deleteAnimal("1")

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Animal not found."
    }

    def "deleteAnimal() - Failed to delete"() {
        given:
        animalRepository.findById(*_) >> Optional.ofNullable(new Animal())
        animalRepository.delete(*_) >> {throw (Exception)}

        when:
        def response = animalServiceImpl.deleteAnimal("1")

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body == "Failed to delete animal."
    }

    def "deleteAnimal() - Success"() {
        given:
        animalRepository.findById(*_) >> Optional.ofNullable(new Animal())

        when:
        def response = animalServiceImpl.deleteAnimal("1")

        then:
        response.statusCode == HttpStatus.OK
    }

    def "parseAndGetAnimalById() - NumberFormatException"() {
        when:
        def animal = animalServiceImpl.parseAndGetAnimalById("invalidId")

        then:
        animal == null
    }
}
