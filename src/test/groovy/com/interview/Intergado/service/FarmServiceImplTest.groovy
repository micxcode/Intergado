package com.interview.Intergado.service

import com.interview.Intergado.dto.FarmDTO
import com.interview.Intergado.repository.FarmRepository
import com.interview.Intergado.repository.domain.Animal
import com.interview.Intergado.repository.domain.Farm
import org.springframework.http.HttpStatus
import spock.lang.Specification

class FarmServiceImplTest extends Specification{
    def farmRepository = Mock(FarmRepository)
    def farmServiceImpl = new FarmServiceImpl(farmRepository)
    def farmDTO = new FarmDTO(1, "farmName", Collections.emptyList())

    def "farmsList() - Return empty"() {
        given:
        farmRepository.findAll() >> Collections.emptyList()

        when:
        def response = farmServiceImpl.farmsList()

        then:
        response.statusCode == HttpStatus.OK
    }

    def "farmsList() - Return list"() {
        given:
        def farm = new Farm()
        farm.id = farmDTO.getId()
        farm.name = farmDTO.getName()
        def animal = new Animal()
        animal.id = 1
        animal.tag = "tag"
        animal.farm = farm
        farm.animals.add(animal)
        farmRepository.findAll() >> Collections.singletonList(farm)

        when:
        def response = farmServiceImpl.farmsList()

        then:
        def responseData = response.body as List<FarmDTO>
        responseData[0].id == farmDTO.getId()
        responseData[0].name == farmDTO.getName()
        responseData[0].animals.size() == 1
        responseData[0].animals.get(0).id == 1
        responseData[0].animals.get(0).tag == "tag"
        responseData[0].animals.get(0).farmName == farmDTO.getName()
        response.statusCode == HttpStatus.OK
    }

    def "add() - Farm already registered"() {
        given:
        farmRepository.findByName(*_) >> new Farm()

        when:
        def response = farmServiceImpl.add(farmDTO)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Farm already registered with the name provided."
    }

    def "add() - Failed to register"() {
        given:
        farmRepository.findByName(farmDTO.getName()) >> null
        farmRepository.save(*_) >> {throw (Exception)}

        when:
        def response = farmServiceImpl.add(farmDTO)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body == "Failed to register new farm."
    }

    def "add() - Success"() {
        given:
        farmRepository.findByName(farmDTO.getName()) >> null

        when:
        def response = farmServiceImpl.add(farmDTO)

        then:
        response.statusCode == HttpStatus.CREATED
    }

    def "updateFarm() - Farm not found"() {
        given:
        farmRepository.findById(1) >> Optional.ofNullable(null)

        when:
        def response = farmServiceImpl.updateFarm("1", farmDTO)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Farm not found."
    }

    def "updateFarm() - Failed to update"() {
        given:
        farmRepository.findById(*_) >> Optional.of(new Farm())
        farmRepository.updateFarm(*_,*_) >> {throw (Exception)}

        when:
        def response = farmServiceImpl.updateFarm("1", farmDTO)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body == "Failed to update farm."
    }

    def "updateFarm() - Success"() {
        given:
        farmRepository.findById(*_) >> Optional.of(new Farm())

        when:
        def response = farmServiceImpl.updateFarm("1", farmDTO)

        then:
        response.statusCode == HttpStatus.NO_CONTENT
    }

    def "deleteFarm() - Farm not found"() {
        given:
        farmRepository.findById(1) >> Optional.ofNullable(null)

        when:
        def response = farmServiceImpl.deleteFarm("1")

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Farm not found."
    }

    def "deleteFarm() - Failed to delete"() {
        given:
        farmRepository.findById(*_) >> Optional.of(new Farm())
        farmRepository.delete(*_) >> {throw (Exception)}

        when:
        def response = farmServiceImpl.deleteFarm("1")

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body == "Failed to delete farm."
    }

    def "deleteFarm() - Success"() {
        given:
        farmRepository.findById(*_) >> Optional.of(new Farm())

        when:
        def response = farmServiceImpl.deleteFarm("1")

        then:
        response.statusCode == HttpStatus.OK
    }

    def "parseAndGetFarmById() - NumberFormatException"() {
        when:
        def farm = farmServiceImpl.parseAndGetFarmById("invalidId")

        then:
        farm == null
    }
}
