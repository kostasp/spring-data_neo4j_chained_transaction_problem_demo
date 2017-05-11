# This is a sample repository aiming to provide insights on the Spring data Chained Transaction Bug found in Neo4jTransactionManager
### Related JIRA that mentions this bug.
https://jira.spring.io/browse/DATAGRAPH-952 <https://jira.spring.io/browse/DATAGRAPH-952

1. It starts an application
2. It creates same tables in postgres
3. It creates a TestNode in neo4j and creates a unique constraint on a property name `uniqueId`
4. It tries to write in parallel more that one TestNodes with the same value in the property `uniqueId`
5. An exception is thrown and it is caught and swallowed in the service layer
6. Then we proceed with to write ENTITY A and ENTITY B to postgres using the chained transaction manager
7. We try to write another entity ENTITY C outside of the initiated transaction
8. We check that entity C is written, whether ENTITY A and ENTITY B are not, because of the following error

```
71102 --- [       Thread-6] o.s.d.n.t.Neo4jTransactionManager        : Commit exception overridden by rollback exception

org.springframework.dao.InvalidDataAccessApiUsageException: Transaction is already closed; nested exception is org.neo4j.ogm.exception.TransactionException: Transaction is already closed
        at org.springframework.data.neo4j.transaction.SessionFactoryUtils.convertOgmAccessException(SessionFactoryUtils.java:139) ~[spring-data-neo4j-4.2.1.RELEASE.jar:na]
        at org.springframework.data.neo4j.transaction.Neo4jTransactionManager.doCommit(Neo4jTransactionManager.java:258) ~[spring-data-neo4j-4.2.1.RELEASE.jar:na]
        at org.springframework.transaction.support.AbstractPlatformTransactionManager.processCommit(AbstractPlatformTransactionManager.java:761) [spring-tx-4.3.8.RELEASE.jar:4.3.8.RELEASE]
        at org.springframework.transaction.support.AbstractPlatformTransactionManager.commit(AbstractPlatformTransactionManager.java:730) [spring-tx-4.3.8.RELEASE.jar:4.3.8.RELEASE]
        at org.springframework.data.transaction.MultiTransactionStatus.commit(MultiTransactionStatus.java:73) [spring-data-commons-1.13.3.RELEASE.jar:na]
        at org.springframework.data.transaction.ChainedTransactionManager.commit(ChainedTransactionManager.java:145) [spring-data-commons-1.13.3.RELEASE.jar:na]
        at org.springframework.transaction.interceptor.TransactionAspectSupport.commitTransactionAfterReturning(TransactionAspectSupport.java:504) [spring-tx-4.3.8.RELEASE.jar:4.3.8.RELEASE]
        at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:292) [spring-tx-4.3.8.RELEASE.jar:4.3.8.RELEASE]
        at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:96) [spring-tx-4.3.8.RELEASE.jar:4.3.8.RELEASE]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179) [spring-aop-4.3.8.RELEASE.jar:4.3.8.RELEASE]
        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:673) [spring-aop-4.3.8.RELEASE.jar:4.3.8.RELEASE]
        at org.test.neo4j.transaction.service.WrapperService$$EnhancerBySpringCGLIB$$e32c2fad.persistAll(<generated>) [classes/:na]
        at org.test.neo4j.transaction.TestService$TestNodeWrapperObject.run(TestService.java:47) [classes/:na]
        at java.lang.Thread.run(Thread.java:745) [na:1.8.0_66]
Caused by: org.neo4j.ogm.exception.TransactionException: Transaction is already closed
        at org.neo4j.ogm.drivers.bolt.transaction.BoltTransaction.commit(BoltTransaction.java:94) ~[neo4j-ogm-bolt-driver-2.1.2.jar:na]
        at org.springframework.data.neo4j.transaction.Neo4jTransactionManager.doCommit(Neo4jTransactionManager.java:256) ~[spring-data-neo4j-4.2.1.RELEASE.jar:na]
        ... 12 common frames omitted

Exception in thread "Thread-6" org.springframework.transaction.HeuristicCompletionException: Heuristic completion: outcome state is rolled back; nested exception is java.lang.NullPointerException
        at org.springframework.data.transaction.ChainedTransactionManager.commit(ChainedTransactionManager.java:172)
        at org.springframework.transaction.interceptor.TransactionAspectSupport.commitTransactionAfterReturning(TransactionAspectSupport.java:504)
        at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:292)
        at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:96)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:673)
        at org.test.neo4j.transaction.service.WrapperService$$EnhancerBySpringCGLIB$$e32c2fad.persistAll(<generated>)
        at org.test.neo4j.transaction.TestService$TestNodeWrapperObject.run(TestService.java:47)
        at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.NullPointerException
        at org.springframework.data.neo4j.transaction.Neo4jTransactionManager.doRollback(Neo4jTransactionManager.java:275)
        at org.springframework.transaction.support.AbstractPlatformTransactionManager.doRollbackOnCommitException(AbstractPlatformTransactionManager.java:900)
        at org.springframework.transaction.support.AbstractPlatformTransactionManager.processCommit(AbstractPlatformTransactionManager.java:789)
        at org.springframework.transaction.support.AbstractPlatformTransactionManager.commit(AbstractPlatformTransactionManager.java:730)
        at org.springframework.data.transaction.MultiTransactionStatus.commit(MultiTransactionStatus.java:73)
        at org.springframework.data.transaction.ChainedTransactionManager.commit(ChainedTransactionManager.java:145)
        ... 8 more

```




## Requirements
* requires docker and docker compose and docker commands available in default terminal user .
** docker installation for mac <https://docs.docker.com/docker-for-mac/install/#install-and-run-docker-for-mac>
* requires unix based terminal to run the bash scripts in this folder


## How To Run

* In order to run it cleanly , make sure you have docker docker-compose and your user can run docker commands without sudo
```./start_containers_run_test_fail_remove_containers.sh```

* In order to run in separate steps

1. 
```./start_all_with_docker_compose.sh```

2.
```./test.sh```
