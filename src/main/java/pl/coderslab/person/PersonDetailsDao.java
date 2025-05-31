package pl.coderslab.person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PersonDetailsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(PersonDetails details) {
        entityManager.persist(details);
    }

    public PersonDetails findById(Long id) {
        return entityManager.find(PersonDetails.class, id);
    }

    public void update(PersonDetails details) {
        entityManager.merge(details);
    }

    public void delete(Long id) {
        PersonDetails details = entityManager.find(PersonDetails.class, id);
        if (details != null) {
            entityManager.remove(details);
        }
    }
}
