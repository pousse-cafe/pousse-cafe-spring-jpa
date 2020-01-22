package poussecafe.spring.jpa.storage;

import java.io.Serializable;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.repository.JpaRepository;
import poussecafe.domain.EntityAttributes;
import poussecafe.domain.EntityDataAccess;
import poussecafe.runtime.OptimisticLockingException;

public abstract class JpaDataAccess<K, D extends EntityAttributes<K>, M extends Serializable> implements EntityDataAccess<K, D>  {

    @Override
    public D findData(K id) {
        return jpaRepository().findById(convertId(id)).orElse(null);
    }

    protected abstract M convertId(K id);

    protected abstract JpaRepository<D, M> jpaRepository();

    @Override
    public void addData(D data) {
        try {
            jpaRepository().save(data);
        } catch (OptimisticLockingFailureException e) {
            throw translateOptimisticLockingFailure(e);
        } catch (DuplicateKeyException e) {
            throw new poussecafe.runtime.DuplicateKeyException(e.getMessage(), e);
        }
    }

    private OptimisticLockingException translateOptimisticLockingFailure(OptimisticLockingFailureException e) {
        return new OptimisticLockingException(e);
    }

    @Override
    public void updateData(D data) {
        try {
            jpaRepository().save(data);
        } catch (OptimisticLockingFailureException e) {
            throw translateOptimisticLockingFailure(e);
        }
    }

    @Override
    public void deleteData(K id) {
        try {
            jpaRepository().deleteById(convertId(id));
        } catch (OptimisticLockingFailureException e) {
            throw translateOptimisticLockingFailure(e);
        }
    }

    @Override
    public void deleteAll() {
        jpaRepository().deleteAll();
    }

    @Override
    public List<D> findAll() {
        return jpaRepository().findAll();
    }

    @Override
    public boolean existsById(K id) {
        return jpaRepository().existsById(convertId(id));
    }
}
