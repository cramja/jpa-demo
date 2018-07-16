/*
 * Autonomic Proprietary 1.0
 *
 * Copyright (c) 2018, Autonomic Incorporated - All rights reserved
 *
 * Proprietary and confidential.
 *
 * NOTICE:  All information contained herein is, and remains the property of
 * Autonomic Incorporated and its suppliers, if any.  The intellectual and
 * technical concepts contained herein are proprietary to Autonomic Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents, patents in
 * process, and are protected by trade secret or copyright law. Dissemination of
 * this information or reproduction of this material is strictly forbidden unless
 * prior written permission is obtained from Autonomic Incorporated.
 *
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 */

package net.spehl.jpa.techtalk.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import net.spehl.jpa.techtalk.Generator;
import net.spehl.jpa.techtalk.model.Person;
import net.spehl.jpa.techtalk.service.PersonService;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/generate")
    public void generate()  {
        List<Person> personList = Generator.createPerson(100, 10, 10);
        service.createPeople(personList);
    }

    @GetMapping("/persons")
    public List<Person> listPersons() {
        return service.listPersons();
    }

    @GetMapping("/persons/{id}")
    public Person getPersons(@PathVariable("id") Long id,
                             @RequestParam(value = "nocache", defaultValue = "false") Boolean noCache) {
        if (noCache) {
            return service.getPerson(id, false);
        }
        return service.getPerson(id);
    }

}
