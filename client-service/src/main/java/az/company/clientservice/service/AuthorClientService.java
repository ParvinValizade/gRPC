package az.company.clientservice.service;



import az.company.authorservice.Author;
import az.company.authorservice.AuthorServiceGrpc;
import com.google.protobuf.Descriptors;
import java.util.Map;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class AuthorClientService {

    @GrpcClient("author-service")
    AuthorServiceGrpc.AuthorServiceBlockingStub synchronousClient;

    public Map<Descriptors.FieldDescriptor, Object> getAuthorById(String authorId) {
        Author authorRequest = Author.newBuilder().setAuthorId(authorId).build();
        Author authorResponse = synchronousClient.getAuthorById(authorRequest);
        return authorResponse.getAllFields();
    }
}
