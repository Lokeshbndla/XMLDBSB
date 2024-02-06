package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.book;
import repository.BookRepository;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final XmlParserService xmlParserService;

    @Autowired
    public BookService(BookRepository bookRepository, XmlParserService xmlParserService) {
        this.bookRepository = bookRepository;
        this.xmlParserService = xmlParserService;
    }

    public void saveDataFromXml(File xmlFile) throws JAXBException {
        List<book> books = xmlParserService.parseXml(xmlFile);

        if (books != null) {
            bookRepository.saveAll(books);
        }
    }
}
