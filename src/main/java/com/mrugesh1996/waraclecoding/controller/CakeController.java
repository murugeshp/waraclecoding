package com.mrugesh1996.waraclecoding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrugesh1996.waraclecoding.entities.Cake;
import com.mrugesh1996.waraclecoding.exceptionhandling.CakeNotFoundException;
import com.mrugesh1996.waraclecoding.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CakeController {

    @Autowired
    CakeService cakeService;

    List<Cake> cakeList;

    @GetMapping(path = "/")
    public List<Cake> geCakes(){
        return cakeService.getCakes();
    }

    @GetMapping(path = "/{id}")
    public Cake getCakes(@PathVariable int id){
        Optional<Cake> cake = cakeService.getCake(id);
        if(!cake.isPresent()){
            throw  new CakeNotFoundException("Cake not Found");
        }
        return cake.get();
    }

    @GetMapping(path = "/cakes")
    public ResponseEntity<byte[]> getCakeJson() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String cakesJsonString = objectMapper.writeValueAsString(cakeService.getCakes());
        byte[] isr = cakesJsonString.getBytes();
        String fileName = "cakes.json";
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "json"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/cakes")
    public List<Cake> createCake(@RequestBody Cake cake) {
        cakeService.createCake(cake.getCakeId(), cake.getTitle(), cake.getDesc(), cake.getImage());
        return cakeService.getCakes();
    }
}
