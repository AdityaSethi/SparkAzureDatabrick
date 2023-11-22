package com.example.controller;


import com.example.model.SparkClientData;
import com.example.model.SparkReadInput;
import com.example.model.SparkWriteInput;
import com.example.services.ReadDataService;
import com.example.services.WriteDataService;
import org.apache.spark.sql.SparkSession;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spark")
public class DeltaSparkController {

    @Autowired
    public DeltaSparkController() {
    }

    @Autowired
    ReadDataService readDataService;

    @Autowired
    WriteDataService writeDataService;

    @RequestMapping(value = "/create/sparksession", method = RequestMethod.POST)
    @ResponseBody
    public String getSparkSession(@RequestBody SparkClientData input) {
        System.out.println("/create/sparksession calling endpoint.........");
        System.out.println("Input param :- "+input);
        return readDataService.createSparkSession(input);
    }

    @RequestMapping(value = "/read/taxidata", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getTaxiData(@RequestBody SparkReadInput input) {
        System.out.println("/read/taxidata calling endpoint.........");
        System.out.println("Input param :- "+input);
        return readDataService.getDataFromContainer(input);
    }

    @RequestMapping(value = "/write/taxidata", method = RequestMethod.POST)
    @ResponseBody
    public String putTaxiData(@RequestBody SparkWriteInput input) {
        System.out.println("/write/taxidata calling endpoint.........");
        System.out.println("Input param :- "+input);
        return writeDataService.writeData(input);
    }

    @RequestMapping(value = "/test")
    public String apiTest() {
        return "sagar api testing.......";
    }
}
