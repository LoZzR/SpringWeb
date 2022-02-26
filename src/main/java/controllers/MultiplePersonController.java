package controllers;

import entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import services.IPersonService;

import java.util.List;

import static util.Functions.COMPARATOR_BY_ID;

@RestController
@RequestMapping("/persons")
public class MultiplePersonController {

    @Autowired
    private IPersonService personService;

    @GetMapping(path = "/list")
    //@ResponseBody
    public List<Person> list() {
        List<Person> persons = personService.findAll();
        persons.sort(COMPARATOR_BY_ID);
        return persons;
    }
}
