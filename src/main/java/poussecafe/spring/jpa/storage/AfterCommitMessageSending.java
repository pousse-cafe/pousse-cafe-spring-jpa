package poussecafe.spring.jpa.storage;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import poussecafe.storage.DefaultMessageCollection;
import poussecafe.storage.MessageCollection;
import poussecafe.storage.MessageSendingPolicy;


public class AfterCommitMessageSending extends MessageSendingPolicy {

    @Override
    public void considerSending(MessageCollection collection) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                sendCollection(collection);
            }
        });
    }

    @Override
    public MessageCollection newMessageCollection() {
        return new DefaultMessageCollection();
    }
}
