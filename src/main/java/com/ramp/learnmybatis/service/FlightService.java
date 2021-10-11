package com.ramp.learnmybatis.service;

import java.util.List;

import javax.annotation.Resource;

import com.ramp.learnmybatis.dal.FlightMapper;

import org.springframework.stereotype.Service;

@Service
public class FlightService {
    
    @Resource
    private FlightMapper flightMapper;

    public List<String> fetchFlightDetails() {
        return flightMapper.fetchFlightDetails();
    }
}
