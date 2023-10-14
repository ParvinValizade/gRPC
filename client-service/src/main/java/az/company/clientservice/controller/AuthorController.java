package az.company.clientservice.controller;

import az.company.clientservice.service.AuthorClientService;
import com.google.protobuf.Descriptors;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

       private final AuthorClientService service;

       @GetMapping("/{authorId}")
       public Map<Descriptors.FieldDescriptor, Object> getAuthorById(@PathVariable String authorId) {
           return service.getAuthorById(authorId);
       }

       @GetMapping("/{authorId}/books")
       public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthor(@PathVariable String authorId) {
           return service.getBooksByAuthor(authorId);
       }

       @GetMapping("/expensive-book")
       public Map<String, Map<Descriptors.FieldDescriptor, Object>> getExpensiveBook() {
           return service.getExpensiveBook();
       }

    @GetMapping("/books/by-gender")
    public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthorGender(@RequestParam String gender) {
        return service.getBooksByAuthorGender(gender);
    }

}
