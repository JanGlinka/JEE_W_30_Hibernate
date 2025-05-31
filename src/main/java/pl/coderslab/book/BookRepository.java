package pl.coderslab.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.person.Person;

@Repository
public interface BookRepository extends JpaRepository <Person, Long>{

}
