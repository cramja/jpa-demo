package net.spehl.jpa.techtalk;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.spehl.jpa.techtalk.model.AtmTxn;
import net.spehl.jpa.techtalk.model.Attribute;
import net.spehl.jpa.techtalk.model.Person;

public class Generator {
    private static Random random = new Random();
    private static char[] letters = "qwertyuiopasdfghjklzxcvbnm".toCharArray();

    public static List<Person> createPerson(int numPerson, int numAttr, int numAtmTxn) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < numPerson; i++) {
            Set<Attribute> attrs = new HashSet<>();
            Person p = randomPerson();
            for (int j = 0; j < numAttr; j++) {
                attrs.add(randomAttr(p));
            }
            Set<AtmTxn> txns = new HashSet<>();
            for (int j = 0; j < numAtmTxn; j++) {
                txns.add(randomTxn(p));
            }
            p.setAttributes(attrs);
            p.setTransactions(txns);
            persons.add(p);
        }
        return persons;
    }

    private static Person randomPerson() {
        return new Person(randomString(20), ((double)random.nextInt(100000))/100);
    }

    private static AtmTxn randomTxn(Person owner) {
        return AtmTxn.builder()
                .setAmount(((double)random.nextInt(1000))/100)
                .setTime(1511058748 + random.nextInt(10000) - 5000)
//                .setOwner(owner)
                .build();
    }

    private static Attribute randomAttr(Person owner) {
        return Attribute.builder()
                .setName(randomString(10))
                .setValue(randomString(100))
                .setType(random.nextInt(10))
//                .setOwner(owner)
                .build();
    }

    private static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = letters[random.nextInt(letters.length)];
        }
        return new String(str);
    }

}
