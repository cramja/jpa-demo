package net.spehl.jpa.techtalk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import net.spehl.jpa.techtalk.model.Person;
import net.spy.memcached.CachedData;
import net.spy.memcached.transcoders.Transcoder;

public class PersonTranscoder implements Transcoder<Person> {

    ObjectMapper mapper;

    public PersonTranscoder() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public boolean asyncDecode(CachedData d) {
        return false;
    }

    @Override
    public CachedData encode(Person o) {
        try {
            String json = mapper.writeValueAsString(o);
            return new CachedData(0, json.getBytes(), json.length());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person decode(CachedData d) {
        try {
            return mapper.readValue(d.getData(), Person.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getMaxSize() {
        return (int) 1e9;
    }
}
