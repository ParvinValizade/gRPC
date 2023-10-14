package az.company.grpcservice.service;


import az.company.TempDB;
import az.company.authorservice.Author;
import az.company.authorservice.AuthorServiceGrpc;
import az.company.authorservice.Book;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void getBooksByAuthor(Author request, StreamObserver<Book> responseObserver) {
        TempDB.getBooksFromTempDb().stream()
                .filter(book -> book.getAuthorId().equals(request.getAuthorId()))
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Book> getExpensiveBook(StreamObserver<Book> responseObserver) {
        return new StreamObserver<Book>() {
            Book expensiveBook = null;
            float priceTrack = 0;
            @Override
            public void onNext(Book book) {
                if (book.getPrice() > priceTrack) {
                    priceTrack = book.getPrice();
                    expensiveBook = book;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(expensiveBook);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Book> getBookByAuthorGender(StreamObserver<Book> responseObserver) {
        return new StreamObserver<Book>() {
            final List<Book> bookList = new ArrayList<>();
            @Override
            public void onNext(Book book) {
                TempDB.getBooksFromTempDb()
                        .stream()
                        .filter(booksFromDb -> book.getAuthorId().equals(booksFromDb.getAuthorId()))
                        .forEach(bookList::add);
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                bookList.forEach(responseObserver::onNext);
                responseObserver.onCompleted();
            }
        };
    }
}
