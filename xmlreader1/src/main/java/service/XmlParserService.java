package service;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import model.book;
import model.catalog;
import repository.BookRepository;

@Service
public class XmlParserService {

    @Value("${xml.file.path}")
    private String xmlFilePath; // Configured in application.properties or application.yml

    private final BookRepository bookRepository;

    private static final Logger logger = Logger.getLogger(XmlParserService.class.getName());

    public XmlParserService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<book> parseXml(File xmlFile) {
        try {
            // Correct JAXBContext creation with your custom catalog class
            JAXBContext jaxbContext = JAXBContext.newInstance(catalog.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Unmarshal the XML data into your catalog object
            catalog catalog = (catalog) unmarshaller.unmarshal(xmlFile);

            // Access and return the parsed book data
            return catalog.getBooks();

        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "Error parsing XML file", e);
            // You might want to handle this exception accordingly, depending on your application requirements.
            return null;
        }
    }
}
