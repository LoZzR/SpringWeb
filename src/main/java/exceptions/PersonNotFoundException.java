package exceptions;

public class PersonNotFoundException extends RuntimeException{

    public static final String PERSON_NOT_FOUND_MSG = "Person with id %s not found";

    public PersonNotFoundException(Long id){
        super(String.format(PERSON_NOT_FOUND_MSG, id));
    }
}
