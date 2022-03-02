package services;

import entities.Person;
import exceptions.InvalidCriteriaException;
import util.CriteriaDto;

import java.util.List;
import java.util.Optional;

public interface IPersonService {
    Optional<Person> findById(Long id);
    Person savePerson(Person person);
    void deletePerson(Person person);
    List<Person> findAll();
    List<Person> getByCriteriaDto(CriteriaDto criteria) throws InvalidCriteriaException;
    int countAllPerson();
}
