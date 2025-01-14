package ru.maxima.spring_security_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxima.spring_security_demo.model.Person;
import ru.maxima.spring_security_demo.repositories.PeopleRepository;

@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public void savePerson(Person person) {
        peopleRepository.save(person);
    }
}
