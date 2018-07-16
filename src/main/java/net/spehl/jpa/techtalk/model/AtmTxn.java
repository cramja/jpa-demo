package net.spehl.jpa.techtalk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class AtmTxn {

    @Column @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column @CreatedDate
    private Date time;

    @Column
    private Double amount;

    @JsonIgnore
    @ManyToOne
    private Person owner;

    public AtmTxn() {}

    public AtmTxn(Date time, Double amount) {
        this.time = time;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public static Builder builder() {
        return new Builder();
    }



    public static final class Builder {

        private Date time;
        private Double amount;
        private Person owner;

        Builder() { }

        public Builder setTime(long millisSince) {
            this.time = new Date(millisSince);
            return this;
        }

        public Builder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public AtmTxn build() {
            AtmTxn atmTxn = new AtmTxn(time, amount);
            atmTxn.setOwner(owner);
            return atmTxn;
        }

        public Builder setOwner(Person owner) {
            this.owner = owner;
            return this;
        }
    }
}
