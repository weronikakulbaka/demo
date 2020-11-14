package com.example.demo.controller;


import com.example.demo.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.CountryRepository;

@Controller
@RequestMapping("/api")
public class CountryController {
     @Autowired
    private CountryRepository countryRepository;

    @Autowired
    public CountryController() {
    }

    @GetMapping("/countries")
    public String getAllCountries(Model theModel)   {
        theModel.addAttribute("countries", countryRepository.findAll());
        return "countries";
    }
    @GetMapping("/countries/delete")
    public String deleteCountry(@RequestParam("id") Integer id, Model theModel) throws Exception {
        countryRepository.deleteById(id);
        theModel.addAttribute("countries", countryRepository.findAll());
        return "countries";
    }
    @RequestMapping(value = "/countries/add", method = RequestMethod.GET)
    public String addCountry(Model theModel)   {
        theModel.addAttribute("country", new Country());
        return "addCountry";
    }
    @RequestMapping(value = "/countries/save", method = RequestMethod.POST)
    public String saveCountry(@ModelAttribute("country") Country country,
                               Model theModel) throws Exception {
        countryRepository.save(country);
        return getAllCountries(theModel);
    }
    @GetMapping("/countries/update")
    public String updateCountry(@RequestParam("id") int id, Model theModel) {
        theModel.addAttribute("country", countryRepository.findById(id));
        return "addCountry";
    }


}
