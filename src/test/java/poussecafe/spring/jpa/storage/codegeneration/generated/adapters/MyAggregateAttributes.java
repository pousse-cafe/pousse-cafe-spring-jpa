package poussecafe.spring.jpa.storage.codegeneration.generated.adapters;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import poussecafe.attribute.Attribute;
import poussecafe.attribute.AttributeBuilder;
import poussecafe.spring.jpa.storage.codegeneration.generated.MyAggregate;
import poussecafe.spring.jpa.storage.codegeneration.generated.MyAggregateId;

public class MyAggregateAttributes implements MyAggregate.Attributes {

    @Override
    public Attribute<MyAggregateId> identifier() {
        return AttributeBuilder.stringId(MyAggregateId.class).read(() -> identifier)
                .write((value) -> identifier = value).build();
    }

    @Id
    private String identifier;

    @Version
    private Long version;
}