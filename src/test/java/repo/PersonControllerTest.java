package repo;

import controllers.PersonController;
import entities.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import services.impl.PersonService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock //Creates mock instance of the field it annotates
    private PersonService mockService;

    @InjectMocks // injects mock dependencies
    private PersonController personController;

    @Test
    void testgetAllPersonsPositive() {
        List<Person> list = new ArrayList<>();
        Person p = new Person();
        p.setId(1L);
        list.add(p);
        when(mockService.findAll()).thenReturn(list);
        ExtendedModelMap model = new ExtendedModelMap();
        String viewName = personController.getAllPersons(model);
        List<Person> persons = (List<Person>) model.get("persons");
        assertAll(
                () -> assertNotNull(persons),
                () -> assertEquals(1, persons.size()),
                () -> assertEquals("persons/list", viewName)
        );
    }
}
