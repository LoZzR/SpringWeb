package controllers;

import entities.Person;
import exceptions.IllegalOperation;
import exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import services.IPersonService;
import util.DateProcessor;

import java.util.List;
import java.util.Optional;

import static util.Functions.COMPARATOR_BY_ID;

@RestController
@RequestMapping("/persons")
public class MultiplePersonController {

    @Autowired
    private IPersonService personService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/list",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    //@ResponseBody
    public List<Person> list() {

        Person p = new Person();
        p.setFirstName("Zakariae");
        p.setLastName("EL HICHEM");
        p.setUsername("Zack");
        p.setPassword("test123");
        p.setHiringDate(DateProcessor.toDate("2022-01-01 22:22:22"));
        personService.savePerson(p);
        List<Person> persons = personService.findAll();
        persons.sort(COMPARATOR_BY_ID);
        throw new RuntimeException();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@Validated(Person.BasicValidation.class)
                         @RequestBody Person person, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalOperation("Cannot save entry!");
        }
        person.setPassword("test123");
        return personService.savePerson(person);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person show(@PathVariable Long id) {
        Optional<Person> personOpt = personService.findById(id);
        if(personOpt.isPresent()) {
            return personOpt.get();
        } else {
            throw new NotFoundException(Person.class, id);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person updatedPerson, @PathVariable
            Long id) {
        Optional<Person> personOpt = personService.findById(id);
        if(personOpt.isPresent()) {
            Person person = personOpt.get();
            person.setUsername(updatedPerson.getUsername());
            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            return personService.savePerson(person);
        } else {
            throw new NotFoundException(Person.class, id);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Person> personOpt = personService.findById(id);
        personOpt.ifPresent(value -> personService.deletePerson(value));
    }
}
