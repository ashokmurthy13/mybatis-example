package com.ramp.learnmybatis.resources;

import java.util.List;

import javax.annotation.Resource;

import com.ramp.learnmybatis.service.FlightService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/flight")
public class FlightController {
    
    @Resource
    private FlightService flightService;

    @GetMapping("")
    public List<String> getFlightDetails() {
        return flightService.fetchFlightDetails();
    }
}
