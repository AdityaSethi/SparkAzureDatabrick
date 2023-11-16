package com.example.controller;


import com.example.services.ReadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spark")
public class DeltaSparkController {

    @Autowired
    public DeltaSparkController() {
    }

    @Autowired
    ReadDataService readDataService;

    @RequestMapping(value = "/read/taxidata", method = RequestMethod.GET)
    public List<String> getTaxiData() {
        return readDataService.getDataFromContainer();
    }

    @RequestMapping(value = "/test")
    public String apiTest() {
        return "sagar api testing.......";
    }
}
