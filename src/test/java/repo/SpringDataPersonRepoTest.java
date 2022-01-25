package repo;

import config.SpringDataConfig;
import entities.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import services.impl.PersonService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDataConfig.class)
public class SpringDataPersonRepoTest {

    @Autowired
    private PersonService personService;

    @BeforeEach
    void setUp(){
        assertNotNull(personService);
    }

    @Test
    void testSavePersonPositive(){
        Person person = new Person();
        person.setFirstName("Zakariae");
        person.setLastName("EL HICHEM");

        assertNotNull(personService.addPerson(person));
        assertEquals(1, personService.findAll().size());
    }

    @Test
    void getPersonByIdPositive(){
        Person person = new Person();
        person.setFirstName("Zakariae");
        person.setLastName("EL HICHEM");

        Person newPerson = personService.addPerson(person);

        assertNotNull(newPerson);
        assertNotNull(newPerson.getId());
        assertNotNull(personService.findById(newPerson.getId()));
    }

}
