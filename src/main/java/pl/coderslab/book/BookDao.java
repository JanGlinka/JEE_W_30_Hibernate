package pl.coderslab.book;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void saveBook(Book book) {
        entityManager.persist(book);
    }

    public Book findById(long id) {
        return entityManager.find(Book.class, id);
    }

    public void update(Book book) {
        entityManager.merge(book);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.find(Book.class, id));
    }

    // ✅ METHOD TO FETCH ALL BOOKS USING JPQL
    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }

    public List<Book> findAllByRating(int rating) {
        return entityManager
                .createQuery("SELECT b FROM Book b WHERE b.rating = :rating", Book.class)
                .setParameter("rating", rating)
                .getResultList();
    }

    public List<Book> findAllWithPublisher() {
        return entityManager
                .createQuery("SELECT b FROM Book b WHERE b.publisher IS NOT NULL", Book.class)
                .getResultList();
    }

    public List<Book> findAllByPublisher(Publisher publisher) {
        return entityManager
                .createQuery("SELECT b FROM Book b WHERE b.publisher = :publisher", Book.class)
                .setParameter("publisher", publisher)
                .getResultList();
    }

    public List<Book> findAllByAuthor(Author author) {
        return entityManager
                .createQuery("SELECT b FROM Book b JOIN b.authors a WHERE a = :author", Book.class)
                .setParameter("author", author)
                .getResultList();
    }


}