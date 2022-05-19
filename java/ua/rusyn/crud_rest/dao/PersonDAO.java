package ua.rusyn.crud_rest.dao;

import org.springframework.stereotype.Component;
import ua.rusyn.crud_rest.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;
    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT,"Sergey"));
        people.add(new Person(++PEOPLE_COUNT,"Vitaliy"));
        people.add(new Person(++PEOPLE_COUNT,"Anna"));
        people.add(new Person(++PEOPLE_COUNT,"Ruslan"));
    }
    public List<Person> index(){
        return people;
    }
    public Person show(int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatePerson) {
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatePerson.getName());
    }

    public void delete(int id) {
        people.removeIf(p-> p.getId() == id);

    }
}
