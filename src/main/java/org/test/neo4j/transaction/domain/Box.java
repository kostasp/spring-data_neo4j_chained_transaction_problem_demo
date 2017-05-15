package org.test.neo4j.transaction.domain;

import javax.persistence.*;

/**
 * Created by kp on 5/11/17.
 */
@Entity
@Table(name = "box")
public class Box {

    @SequenceGenerator(name="boxSeq", sequenceName="box_seq")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boxSeq")
    private Long id;
    private String name;
    private String description;

    public Box(){

    }


    public Box(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        final StringBuffer sb = new StringBuffer("Box{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
