package poussecafe.spring.jpa.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import poussecafe.storage.TransactionRunner;

public class SpringJpaTransactionRunner implements TransactionRunner {

    @Override
    public void runInTransaction(Runnable runnable) {
        TransactionStatus status = platformTransactionManager.getTransaction(null);
        try {
            runnable.run();
        } catch(Exception e) {
            platformTransactionManager.rollback(status);
        } finally {
            if(!status.isCompleted()) {
                platformTransactionManager.commit(status);
            }
        }
    }

    @Autowired
    private PlatformTransactionManager platformTransactionManager;
}
