package net.spehl.jpa.techtalk.repo;

import java.util.List;
import net.spehl.jpa.techtalk.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.attributes LEFT JOIN FETCH p.transactions WHERE p.balance < :balance")
    List<Person> personByBalanceFetch(@Param("balance") double balance);

    @Query("SELECT p FROM Person p WHERE p.balance < :balance")
    List<Person> personByBalanceNoFetch(@Param("balance") double balance);

    @Query("SELECT p FROM Person p")
    List<Person> listPeople();

    @Query("SELECT p FROM Person p WHERE p.id = :id")
    Person getPersonById(@Param("id") Long id);


}
