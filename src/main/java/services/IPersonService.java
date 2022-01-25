package services;

import entities.Person;

import java.util.List;

public interface IPersonService {
    Person findById(Long id);
    Person addPerson(Person person);
    List<Person> findAll();
    int countAllPerson();
}
