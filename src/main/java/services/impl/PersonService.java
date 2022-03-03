package services.impl;

import entities.Person;
import exceptions.InvalidCriteriaException;
import exceptions.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo.SpringDataPersonRepo;
import services.IPersonService;
import util.CriteriaDto;
import util.DateProcessor;
import util.FieldGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private SpringDataPersonRepo personRepo;

    @Override
    public Optional<Person> findById(Long id) {
        return personRepo.findById(id);
    }

    @Override
    public Person savePerson(Person person) {
        return personRepo.save(person);
    }

    @Override
    public void deletePerson(Person person) {
        personRepo.delete(person);
    }

    @Override
    public List<Person> findAll() {
        return personRepo.findAll();
    }

    @Override
    public List<Person> getByCriteriaDto(CriteriaDto criteria) throws InvalidCriteriaException {
        List<Person> persons = new ArrayList<>();
        FieldGroup fg = FieldGroup.getField(criteria.getFieldName());

        switch (fg) {
            case FIRSTNAME:
                persons = criteria.getExactMatch() ? personRepo.findByFirstName(criteria.getFieldValue())
                        : personRepo.findByFirstNameLike(criteria.getFieldValue());
                break;
            case LASTNAME:
                persons = criteria.getExactMatch() ? personRepo.findByLastName(criteria.getFieldValue())
                        : personRepo.findByLastNameLike(criteria.getFieldValue());
                break;
            case USERNAME:
                if(criteria.getExactMatch()) {
                    Optional<Person> persOpt = personRepo.findByUsername(criteria.getFieldValue());
                    if(persOpt.isPresent()) {
                        persons.add(persOpt.get());
                    }
                } else {
                    persons = personRepo.findByUsernameLike(criteria.getFieldValue());
                }
                break;
            case HIREDIN:
                LocalDateTime date;SS
                try {
                    date = DateProcessor.toDate(criteria.getFieldValue());
                } catch (DateTimeParseException e) {
                    throw new InvalidCriteriaException("fieldValue", "typeMismatch.hiringDate");
                }
                persons = personRepo.findByHiringDate(date);
                break;
        }
        return persons;
    }
    @Override
    public int countAllPerson() {
        return findAll().size();
    }
}
