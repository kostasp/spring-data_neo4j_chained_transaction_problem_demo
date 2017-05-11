# This is a sample repository aiming to provide insights on the Spring data Chained Transaction Bug found in Neo4jTransactionManager
### Related JIRA that mentions this bug.
https://jira.spring.io/browse/DATAGRAPH-952 <https://jira.spring.io/browse/DATAGRAPH-952

1. It starts an application
2. It creates same tables in postgres
3. It creates a TestNode in neo4j and creates a unique constraint on a property name `uniqueId`
4. It tries to write in parallel more that one TestNodes with the same value in the property `uniqueId`
5. An exception is thrown and it is caught and swallowed in the service layer
6. Then a test 


## Requirements
* requires docker and docker compose and docker commands available in default terminal user .
** docker installation for mac <https://docs.docker.com/docker-for-mac/install/#install-and-run-docker-for-mac>
* requires unix based terminal to run the bash scripts in this folder


