package exceptions;

import entities.Person;

public class PersonNotFoundException extends NotFoundException{

    public PersonNotFoundException(Long id){
        super(Person.class, id);
    }
}
