package services;

import entities.Person;

import java.util.List;

public interface IPersonService {
    Person findById(Long id);
    List<Person> findAll();
    int countAllPerson();
}
