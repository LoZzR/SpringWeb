package services;

import entities.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {
    Optional<Person> findById(Long id);
    Person savePerson(Person person);
    void deletePerson(Person person);
    List<Person> findAll();
    int countAllPerson();
}
