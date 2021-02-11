package poussecafe.spring.jpa.storage;

import poussecafe.storage.MessageSendingPolicy;
import poussecafe.storage.Storage;
import poussecafe.storage.TransactionRunner;

public class SpringJpaStorage extends Storage {

    public static final String NAME = "SpringJpa";

    public static SpringJpaStorage instance() {
        return INSTANCE;
    }

    private static final SpringJpaStorage INSTANCE = new SpringJpaStorage();

    @Override
    protected MessageSendingPolicy initMessageSendingPolicy() {
        return new AfterCommitMessageSending();
    }

    @Override
    protected TransactionRunner initTransactionRunner() {
        return new SpringJpaTransactionRunner();
    }

    private SpringJpaStorage() {

    }

    @Override
    public String name() {
        return NAME;
    }
}
