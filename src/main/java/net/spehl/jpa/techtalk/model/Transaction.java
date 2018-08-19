package net.spehl.jpa.techtalk.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "transactions")
public class Transaction {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Double difference;

    String attributesMapJson;

    public Transaction() {}

    public Transaction(String name,
                       Double difference) {
        this.name = name;
        this.difference = difference;
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

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    @Column(nullable = false,
            name ="attributesMapJson"
    )
    public String getAttributesMapJson() {
        return attributesMapJson;
    }

    public void setAttributesMapJson(String attributesMapJson) {
        this.attributesMapJson = attributesMapJson;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private String name = "";
        private Double difference;
        private String attributesJsonMap = "{}";

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDifference(Double difference) {
            this.difference = difference;
            return this;
        }

        public Builder setAttributesJsonMap(String attributesJsonMap) {
            this.attributesJsonMap = attributesJsonMap;
            return this;
        }

        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.setName(name);
            transaction.setDifference(difference);
            transaction.setAttributesMapJson(attributesJsonMap);
            return transaction;
        }
    }
}
