package net.spehl.jpa.techtalk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Attribute {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String value;

    @Column
    private int type;

    @ManyToOne
    @JsonIgnore
    private Person owner;

    public Attribute() {}

    public Attribute(String name,
                     String value,
                     int type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String name;
        private String value;
        private int type;
        private Person owner;

        private Builder() {
        }

        public static Builder anAttribute() {
            return new Builder();
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setOwner(Person owner) {
            this.owner = owner;
            return this;
        }

        public Attribute build() {
            Attribute attribute = new Attribute(name, value, type);
            attribute.setOwner(owner);
            return attribute;
        }
    }
}
