package pl.coderslab.book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final PublisherDao publisherDao;
    private final AuthorDao authorDao;

    @RequestMapping("/all")
    @ResponseBody
    public String getAllBooks() {
        List<Book> books = bookDao.findAll();
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append("ID: ").append(book.getId())
                    .append(", Title: ").append(book.getTitle())
                    .append(", Rating: ").append(book.getRating())
                    .append(", Description: ").append(book.getDescription())
                    .append("\n");
        }
        return sb.toString();
    }

    @RequestMapping("/rating/{rating}")
    @ResponseBody
    public String getBooksByRating(@PathVariable int rating) {
        List<Book> books = bookDao.findAllByRating(rating);
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append("ID: ").append(book.getId())
                    .append(", Title: ").append(book.getTitle())
                    .append(", Rating: ").append(book.getRating())
                    .append(", Description: ").append(book.getDescription())
                    .append("\n");
        }
        return sb.toString();
    }

    public BookController(BookDao bookDao, PublisherDao publisherDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.publisherDao = publisherDao;
        this.authorDao = authorDao;
    }

    @RequestMapping("/add")
    @ResponseBody
    public String hello() {
        Publisher publisher = new Publisher();
        publisher.setName("Helion 2");
        publisherDao.savePublisher(publisher);
        Book book = new Book("Thinking in Java 2", 6, "Book from controller 2");
        book.setPublisher(publisher);
        bookDao.saveBook(book);
        return "Id dodanej książki to:" + book.getId();
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    public String getBook(@PathVariable long id) {
        Book book = bookDao.findById(id);
        return book.toString();
    }

    @RequestMapping("/update/{id}/{title}")
    @ResponseBody
    public String updateBook(@PathVariable long id, @PathVariable String title) {
        Book book = bookDao.findById(id);
        book.setTitle(title);
        bookDao.update(book);
        return book.toString();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public String deleteBook(@PathVariable long id) {
        bookDao.delete(id);
        return "deleted";
    }

    @RequestMapping("/publishers/all")
    @ResponseBody
    public String getAllPublishers() {
        List<Publisher> publishers = publisherDao.findAll();
        StringBuilder sb = new StringBuilder();
        for (Publisher p : publishers) {
            sb.append("ID: ").append(p.getId())
                    .append(", Name: ").append(p.getName())
                    .append("\n");
        }
        return sb.toString();
    }

    @RequestMapping("/add-with-authors")
    @ResponseBody
    public String addBookWithAuthors() {
        // Tworzymy wydawcę
        Publisher publisher = new Publisher();
        publisher.setName("PWN");
        publisherDao.savePublisher(publisher);

        // Tworzymy książkę
        Book book = new Book("Effective Java", 10, "Java best practices");
        book.setPublisher(publisher);

        // Pobieramy dwóch autorów po ich ID
        Author author1 = authorDao.findById(1L);
        Author author2 = authorDao.findById(2L);

        // Łączymy książkę z autorami
        book.setAuthors(List.of(author1, author2));

        // Zapisujemy książkę wraz z powiązaniami
        bookDao.saveBook(book);

        return "Dodano książkę z ID: " + book.getId();
    }

    @RequestMapping("/with-publisher")
    @ResponseBody
    public String booksWithPublisher() {
        List<Book> books = bookDao.findAllWithPublisher();
        return books.toString();
    }

    @RequestMapping("/by-publisher/{id}")
    @ResponseBody
    public String booksByPublisher(@PathVariable Long id) {
        Publisher publisher = publisherDao.findById(id);
        List<Book> books = bookDao.findAllByPublisher(publisher);
        return books.toString();
    }

    @RequestMapping("/by-author/{id}")
    @ResponseBody
    public String booksByAuthor(@PathVariable Long id) {
        Author author = authorDao.findById(id);
        List<Book> books = bookDao.findAllByAuthor(author);
        return books.toString();
    }

}