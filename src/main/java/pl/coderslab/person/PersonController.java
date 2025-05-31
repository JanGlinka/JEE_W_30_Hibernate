package pl.coderslab.person;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonDao personDao;
    private final PersonDetailsDao personDetailsDao;

    public PersonController(PersonDao personDao, PersonDetailsDao personDetailsDao) {
        this.personDao = personDao;
        this.personDetailsDao = personDetailsDao;
    }

    // CREATE
    @PostMapping("/add")
    @ResponseBody
    public String addPerson(@RequestParam String login,
                            @RequestParam String password,
                            @RequestParam String email,
                            @RequestParam String firstName,
                            @RequestParam String lastName,
                            @RequestParam String streetNumber,
                            @RequestParam String street,
                            @RequestParam String city) {

        PersonDetails details = new PersonDetails();
        details.setFirstName(firstName);
        details.setLastName(lastName);
        details.setStreetNumber(streetNumber);
        details.setStreet(street);
        details.setCity(city);

        Person person = new Person();
        person.setLogin(login);
        person.setPassword(password);
        person.setEmail(email);
        person.setPersonDetails(details);

        personDao.save(person);

        return "Dodano osobę o ID: " + person.getId();
    }

    // READ
    @GetMapping("/get/{id}")
    @ResponseBody
    public String getPerson(@PathVariable Long id) {
        Person person = personDao.findById(id);
        if (person == null) return "Nie znaleziono osoby";

        PersonDetails d = person.getPersonDetails();

        return "Person[id=" + person.getId() + ", login=" + person.getLogin() + ", email=" + person.getEmail() +
                "] Details[firstName=" + d.getFirstName() + ", lastName=" + d.getLastName() + ", streetNumber=" + d.getStreetNumber() +
                ", street=" + d.getStreet() + ", city=" + d.getCity() + "]";
    }

    // UPDATE - przykładowo aktualizacja email i miasta
    @PostMapping("/update/{id}")
    @ResponseBody
    public String updatePerson(@PathVariable Long id,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String city) {
        Person person = personDao.findById(id);
        if (person == null) return "Nie znaleziono osoby";

        if (email != null) person.setEmail(email);
        if (city != null && person.getPersonDetails() != null) {
            person.getPersonDetails().setCity(city);
        }

        personDao.update(person);

        return "Zaktualizowano osobę o ID: " + id;
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deletePerson(@PathVariable Long id) {
        personDao.delete(id);
        return "Usunięto osobę o ID: " + id;
    }
}
