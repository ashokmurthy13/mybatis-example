package com.ramp.learnmybatis.dal;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface FlightMapper {
    
    @Select("SELECT \"AIRLINE\" FROM flights_tbl LIMIT 5")
    List<String> fetchFlightDetails();
}
