package controllers;

import entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import services.IPersonService;
import util.DateProcessor;

import java.util.List;

import static util.Functions.COMPARATOR_BY_ID;

@RestController
@RequestMapping("/persons")
public class MultiplePersonController {

    @Autowired
    private IPersonService personService;

    @GetMapping(path = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseBody
    public List<Person> list() {

        Person p = new Person();
        p.setFirstName("Zakariae");
        p.setLastName("EL HICHEM");
        p.setUsername("Zack");
        p.setPassword("test123");
        p.setHiringDate(DateProcessor.toDate("2022-01-01 22:22:22"));
        personService.addPerson(p);
        List<Person> persons = personService.findAll();
        persons.sort(COMPARATOR_BY_ID);
        return persons;
    }
}
