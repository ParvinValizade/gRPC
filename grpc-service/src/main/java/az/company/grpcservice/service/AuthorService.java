package az.company.grpcservice.service;


import az.company.TempDB;
import az.company.authorservice.Author;
import az.company.authorservice.AuthorServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthorService extends AuthorServiceGrpc.AuthorServiceImplBase {

    @Override
    public void getAuthorById(Author request, StreamObserver<Author> responseObserver) {
        TempDB.getAuthorsFromTempDb().stream()
                .filter(author -> author.getAuthorId().equals(request.getAuthorId()))
                .findFirst()
                .ifPresent(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
