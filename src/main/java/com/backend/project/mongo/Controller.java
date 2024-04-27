package com.backend.project.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    private static RegexRepository regexRepository;

    @Autowired
    public  Controller(RegexRepository regexRepository){
        this.regexRepository=regexRepository;
    }

    @GetMapping("/")
    public static List<RegexRecord> getAllRegex(){
        return regexRepository.findAll();
    }

    @PostMapping("/addRegex")
    public static void addRegex(@RequestBody RegexRecord regexRecord){
        regexRepository.save(regexRecord);
    }

    @DeleteMapping("/delete/{id}")
    public static void deleteRegex(@PathVariable("id") String id){
        regexRepository.deleteById(id);

    }

    @GetMapping("/{regex}")
    public static RegexRecord getRegex(@PathVariable("regex") String regex){
        return regexRepository.findTopByRegex(regex);
    }
}
