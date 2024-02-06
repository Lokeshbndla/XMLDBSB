package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import service.BookService;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }

        try {
            // Convert MultipartFile to File
            File xmlFile = convertMultiPartToFile(multipartFile);

            // Call the service method with the File
            bookService.saveDataFromXml(xmlFile);

            return ResponseEntity.ok("File uploaded and data saved successfully");
        } catch (IOException | JAXBException e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            return ResponseEntity.status(500).body("Error occurred while processing the file");
        }
    }

    // Helper method to convert MultipartFile to File
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
    }
}
