package pl.coderslab.book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PublisherDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void savePublisher(Publisher publisher) {
        entityManager.persist(publisher);
    }

    public List<Publisher> findAll() {
        return  entityManager
                .createQuery("SELECT p FROM Publisher p", Publisher.class)
                .getResultList();
    }


    public Publisher findById(Long id) {
        return entityManager.find(Publisher.class, id);
    }

}
