
package net.spehl.jpa.techtalk.service;

import java.util.List;
import net.spehl.jpa.techtalk.model.Person;
import net.spehl.jpa.techtalk.repo.PersonRepository;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    MemcachedClient client;
    PersonRepository repository;
    PersonTranscoder transcoder = new PersonTranscoder();

    public PersonService(
            PersonRepository repository,
            MemcachedClient client) {
        this.repository = repository;
        this.client = client;
    }

    public Person getPerson(Long id) {
        Person p = client.get(id + "", transcoder);
        if (p != null) {
            return p;
        }

        p = repository.getPersonById(id);
        if (p == null) {
            return null;
        }

        client.add("" + id, 1600, p, transcoder);
        return p;
    }

    public Person getPerson(Long id, boolean usecache) {
        if (!usecache) {
            return repository.getPersonById(id);
        }
        return getPerson(id);
    }


    public List<Person> listPersons() {
        long startTime = System.currentTimeMillis();
        List<Person> persons = repository.listPeople();
        for (Person p : persons) {
            client.add("" + p.getId(), 1600, p, transcoder);
        }
        logger.info("time to cache all {} people: {}", persons.size(), System.currentTimeMillis() - startTime);
        return persons;
    }

    public void createPeople(List<Person> personList) {
        repository.save(personList);
    }
}
