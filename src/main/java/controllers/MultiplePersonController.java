package controllers;

import entities.Person;
import exceptions.IllegalOperation;
import exceptions.InvalidCriteriaException;
import exceptions.NotFoundException;
import exceptions.PersonsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;
import services.IPersonService;
import util.CriteriaDto;
import util.DateProcessor;

import javax.servlet.http.HttpServletResponse;
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
        return persons;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> processSubmit(@Validated @RequestBody CriteriaDto criteria) {
        try {
            return personService.getByCriteriaDto(criteria);
        } catch (InvalidCriteriaException ice) {
            throw  new PersonsException(HttpStatus.BAD_REQUEST, ice);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@Validated(Person.BasicValidation.class)
                         @RequestBody Person person, BindingResult result,
                       @Value("#{request.requestURL}")
                               StringBuffer originalUrl, HttpServletResponse response) {
        if (result.hasErrors()) {
            String errString = createErrorString(result);
            throw new PersonsException(HttpStatus.BAD_REQUEST,"Cannot save entry because: "+ errString);
        }
        if(StringUtils.isEmpty(person.getPassword())){
            person.setPassword("test123");
        }
        try {
            Person newPerson = personService.savePerson(person);
            response.setHeader("Location", getLocationForUser(
                    originalUrl, newPerson.getId()));
        } catch (Exception e) {
            throw new PersonsException(HttpStatus.UNPROCESSABLE_ENTITY, e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person show(@PathVariable Long id) {
        Optional<Person> personOpt = personService.findById(id);
        if(personOpt.isPresent()) {
            return personOpt.get();
        } else {
            throw new PersonsException(HttpStatus.NOT_FOUND, "Unable to find entry with id " + id );
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
            throw new PersonsException(HttpStatus.NOT_FOUND, "Unable to find entry with id " + id );
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Person> personOpt = personService.findById(id);
        personOpt.ifPresent(value -> personService.deletePerson(value));
    }

    private String createErrorString(BindingResult result) {
        StringBuilder sb =  new StringBuilder();
        result.getAllErrors().forEach(error -> {
            if(error instanceof FieldError) {
                FieldError err= (FieldError) error;
                sb.append("Field '").append(err.getField()).append("' value error: ").append(err.getDefaultMessage());
            }
        });
        return sb.toString();
    }

    private static String getLocationForUser(StringBuffer url,
                                             Object childIdentifier) {
        UriTemplate template = new UriTemplate(url.toString() + "/{id}");
        return template.expand(childIdentifier).toASCIIString();
    }
}
