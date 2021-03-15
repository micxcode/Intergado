package com.interview.Intergado.controller

import com.interview.Intergado.dto.FarmDTO
import com.interview.Intergado.service.FarmService
import spock.lang.Specification

class FarmControllerTest extends Specification{
    def farmService = Mock(FarmService)
    def farmDTO = Mock(FarmDTO)
    def farmController = new FarmController(farmService)

    def "add farm"() {
        when:
        farmController.add(farmDTO)
        then:
        1 * farmService.add(farmDTO)
    }

    def "update farm"() {
        when:
        farmController.updateFarm("farm_id", farmDTO)
        then:
        1 * farmService.updateFarm("farm_id", farmDTO)
    }

    def "farm list"() {
        when:
        farmController.farmsList()
        then:
        1 * farmService.farmsList()
    }

    def "delete farm"() {
        when:
        farmController.deleteFarm("farm_id")
        then:
        1 * farmService.deleteFarm("farm_id")
    }
}
