package controllers;

import entities.Person;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import services.impl.PersonService;

import java.util.Optional;

@Controller
@RequestMapping("/persons/{id}")
public class SinglePersonController {

    private PersonService personService;

    public SinglePersonController(PersonService personService) {
        this.personService = personService;
    }

    @ModelAttribute
    protected Person modelPerson(@PathVariable Long id){
        Person person = new Person();
        Optional<Person> personOpt = Optional.of(personService.findById(id));
        return personOpt.orElse(person);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String show() {
        return "persons/show";
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String update(@Validated Person person, BindingResult result){
        // do some logic
        return "redirect:/persons/" + person.getId();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Validated Person person, BindingResult result){
        //do some logic
        return "redirect:/persons/" + person.getId();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(){
        //do some logic
        return "redirect:/persons/list";
    }
}
