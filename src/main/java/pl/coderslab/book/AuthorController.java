package pl.coderslab.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorDao authorDao;

    public AuthorController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @RequestMapping("/add")
    @ResponseBody
    public String addAuthors() {
        Author a1 = new Author();
        a1.setFirstName("Joshua");
        a1.setLastName("Bloch");

        Author a2 = new Author();
        a2.setFirstName("Robert");
        a2.setLastName("Martin");

        authorDao.save(a1);
        authorDao.save(a2);

        return "Dodano autorów: " + a1.getId() + ", " + a2.getId();
    }

    @RequestMapping("/all")
    @ResponseBody
    public String getAllAuthors() {
        List<Author> authors = authorDao.findAll();
        StringBuilder sb = new StringBuilder();
        for (Author a : authors) {
            sb.append("ID: ").append(a.getId())
                    .append(", Name: ").append(a.getFirstName()).append(" ").append(a.getLastName())
                    .append("\n");
        }
        return sb.toString();
    }
}
