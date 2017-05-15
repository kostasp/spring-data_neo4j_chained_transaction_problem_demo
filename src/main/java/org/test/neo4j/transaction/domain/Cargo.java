package org.test.neo4j.transaction.domain;

import javax.persistence.*;

/**
 * Created by kp on 5/11/17.
 */
@Entity
@Table(name = "cargo")
public class Cargo {

    private static final long serialVersionUID = -3009157732242241606L;
    @SequenceGenerator(name="cargoSeq", sequenceName="cargo_seq")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargoSeq")
    private Long id;
    private String name;
    private String description;

    public Cargo(){

    }

    public Cargo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cargo{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
