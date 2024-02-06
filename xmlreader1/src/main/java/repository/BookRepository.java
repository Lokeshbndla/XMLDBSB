package repository;


import model.book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<book, Long> {

	book findByTitleAndAuthor(String title, String author);
    // Additional custom queries can be added here if needed
}

