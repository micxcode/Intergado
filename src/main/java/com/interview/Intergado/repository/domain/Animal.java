package com.interview.Intergado.repository.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tag;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "farm_id")
    private Farm farm;

    public Animal(final String tag, final Farm farm) {
        this.tag = tag;
        this.farm = farm;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", farm=" + farm +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Animal animal = (Animal) o;

        return id != null ? id.equals(animal.id) : animal.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
