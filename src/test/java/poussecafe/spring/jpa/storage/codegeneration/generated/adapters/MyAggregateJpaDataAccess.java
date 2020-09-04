package poussecafe.spring.jpa.storage.codegeneration.generated.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import poussecafe.discovery.DataAccessImplementation;
import poussecafe.spring.jpa.storage.JpaDataAccess;
import poussecafe.spring.jpa.storage.SpringJpaStorage;
import poussecafe.spring.jpa.storage.codegeneration.generated.MyAggregate;
import poussecafe.spring.jpa.storage.codegeneration.generated.MyAggregateDataAccess;
import poussecafe.spring.jpa.storage.codegeneration.generated.MyAggregateId;

@DataAccessImplementation(
    aggregateRoot = MyAggregate.class,
    dataImplementation = MyAggregateAttributes.class,
    storageName = SpringJpaStorage.NAME
)
public class MyAggregateJpaDataAccess extends JpaDataAccess<MyAggregateId, MyAggregateAttributes, String> implements
        MyAggregateDataAccess<MyAggregateAttributes> {

    @Override
    protected String convertId(MyAggregateId key) {
        return key.stringValue();
    }

    @Override
    protected MyAggregateDataJpaRepository jpaRepository() {
        return repository;
    }

    @Autowired
    private MyAggregateDataJpaRepository repository;
}