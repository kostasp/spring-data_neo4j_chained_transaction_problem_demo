package org.test.neo4j.transaction.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

/**
 * Created by kp on 5/11/17.
 */

@NodeEntity(label = "TEST_NODE")
public class TestNode implements Serializable {
    private static final long serialVersionUID = -9132746542241161523L;
    @GraphId
    private Long nodeId;

    private String uniqueId;
    private String name;

    public TestNode() {

    }


    public TestNode(String uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
}
