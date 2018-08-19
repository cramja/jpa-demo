package net.spehl.jpa.techtalk.repo;

import net.spehl.jpa.techtalk.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Long countByName(String name);

}
