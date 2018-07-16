package net.spehl.jpa.techtalk;

import static com.google.common.math.Stats.meanOf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.spehl.jpa.techtalk.model.Person;
import net.spehl.jpa.techtalk.repo.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired PersonRepository repo;

	@Test
	public void testPerformance() {
		final int trials = 5;
		List<Person> people = Generator.createPerson(1000, 100, 50);
		long startTime = System.nanoTime();
		repo.save(people);
		System.out.printf("%-30d - save%n", System.nanoTime() - startTime);
		// find mean balance
		List<Double> balances = people.stream().map(Person::getBalance).collect(Collectors.toList());
		Double mean = meanOf(balances);

		List<Long> xpTimes = new ArrayList<>();
		List<Long> n1Times = new ArrayList<>();
		for (int i = 0; i < trials; i++) {
			System.out.println(i);
			xpTimes.add(selectCrossProduct(mean));
			n1Times.add(selectNPlus1(mean));
		}
		System.out.printf("%-30f - xprod1 - 50%n", meanOf(xpTimes)/1e6);
		System.out.printf("%-30f - np1    - 50%n", meanOf(n1Times)/1e6);

		double fivePercent = balances.get(balances.size() / 20);
		xpTimes.clear();
		n1Times.clear();
		for (int i = 0; i < trials; i++) {
			System.out.println(i);
			xpTimes.add(selectCrossProduct(fivePercent));
			n1Times.add(selectNPlus1(fivePercent));
		}
		System.out.printf("%-30f - xprod1 - 5%n", meanOf(xpTimes)/1e6);
		System.out.printf("%-30f - np1    - 5%n", meanOf(n1Times)/1e6);

	}

	public long selectNPlus1(double balance) {
		long startTime = System.nanoTime();
		List<Person> p = repo.personByBalanceNoFetch(balance);
		return System.nanoTime() -startTime;
	}

	public long selectCrossProduct(double balance) {
		long startTime = System.nanoTime();
		List<Person> p = repo.personByBalanceFetch(balance);
		return System.nanoTime() - startTime;
	}

}
