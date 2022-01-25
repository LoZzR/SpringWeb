package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import services.IPersonService;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private IPersonService personService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getAllPersons(Model model){
        model.addAttribute("persons", personService.findAll());
        return "persons";
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getPersonById(Model model, @PathVariable Long id){
        model.addAttribute("person", personService.findById(id));
        return "person";
    }
}
