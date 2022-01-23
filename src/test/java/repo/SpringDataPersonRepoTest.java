package repo;

import config.SpringDataConfig;
import entities.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDataConfig.class)
public class SpringDataPersonRepoTest {

    @Autowired
    private SpringDataPersonRepo personRepo;

    @BeforeEach
    void setUp(){
        assertNotNull(personRepo);
    }

    @Test
    void testSavePersonPositive(){
        Person person = new Person();
        person.setFirstName("Zakariae");
        person.setLastName("EL HICHEM");

        assertNotNull(personRepo.save(person));
        assertEquals(1, personRepo.findAll().size());
    }

}
