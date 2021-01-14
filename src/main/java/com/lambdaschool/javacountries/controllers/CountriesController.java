package com.lambdaschool.javacountries.controllers;

import com.lambdaschool.javacountries.models.Country;
import com.lambdaschool.javacountries.repositories.CheckCountry;
import com.lambdaschool.javacountries.repositories.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountriesController {
    @Autowired
    private CountriesRepository countriesRepository;

//    http://localhost:2019/names/all

    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> listAllCountries() {
        List<Country> myList = new ArrayList<>();
        countriesRepository.findAll().iterator().forEachRemaining(myList::add);

        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

//    http://localhost:2019/names/start/u
    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> findByName(@PathVariable char letter) {
        List<Country> myList = new ArrayList<>();
        countriesRepository.findAll().iterator().forEachRemaining(myList::add);

        List<Country> filteredList = filterCountries(myList, (c) -> (c.getName().charAt(0) == letter));

        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }
    private List<Country> filterCountries(List<Country> countryList, CheckCountry tester) {
        List<Country> rtnList = new ArrayList<>();

        for(Country c : countryList) {
            if(tester.test(c)){
                rtnList.add(c);
            }
        }
        return rtnList;
    }
//    http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = "application/json")
    public ResponseEntity<?> findPopTotal(){
        List<Country> myList = new ArrayList<>();
        countriesRepository.findAll().iterator().forEachRemaining(myList::add);

        int total = 0;
        for( Country c: myList){
            total += c.getPopulation();
        }

        return new ResponseEntity<>(total, HttpStatus.OK);
    }
//    http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> findPopMin() {
        List<Country> myList = new ArrayList<>();
        countriesRepository.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));
        Country minPopulation = myList.get(0);
        return new ResponseEntity<>(minPopulation, HttpStatus.OK);
    }
//    http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = "application/json")
    public ResponseEntity<?> findPopMax() {
        List<Country> myList = new ArrayList<>();
        countriesRepository.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c2, c1) -> (int)(c2.getPopulation() - c1.getPopulation()));
        Country maxPopulation = myList.get(0);
        return new ResponseEntity<>(maxPopulation, HttpStatus.OK);
    }
}
