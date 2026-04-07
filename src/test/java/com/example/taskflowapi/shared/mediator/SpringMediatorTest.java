package com.example.taskflowapi.shared.mediator;

import com.example.taskflowapi.shared.mediator.impl.SpringMediator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpringMediatorTest {
    @Mock
    private TransactionTemplate transactionTemplate;

    private Mediator mediator;

    /* Test records */
    record CreateUserCommand(String name) implements Command<String> {}
    record GetUserQuery(String id) implements Query<String> {}
    record UnknownRequest() implements Request<String> {}

    /* Test handlers */
    private CommandHandler<CreateUserCommand, String> commandHandlerSpy;
    private QueryHandler<GetUserQuery, String> queryHandlerSpy;

    @BeforeEach
    void setUp() {
        commandHandlerSpy = spy(
                new CommandHandler<CreateUserCommand, String>() {
                    @Override
                    public String handle(CreateUserCommand command) {
                        return "User Created";
                    }
                }
        );
        queryHandlerSpy = spy(
                new QueryHandler<GetUserQuery, String>() {
                    @Override
                    public String handle(GetUserQuery query) {
                        return "User Founded";
                    }
                }
        );

        lenient().when(transactionTemplate.execute(any())).thenAnswer(
                invocation -> {
                    TransactionCallback<?> callback = invocation.getArgument(0);
                    return callback.doInTransaction(null);
                }
        );
    }

    @Test
    @DisplayName("Debe rutear un Command a su handler y ejecutarlo en una transacción")
    void shouldRouteCommandToHandlerAndExecuteInTransaction() {
        var command = new CreateUserCommand("Jenner");
        var result = mediator.send(command);

        assertEquals("User Created", result);
        verify(commandHandlerSpy).handle(command);
        verify(transactionTemplate).execute(any());
    }

    @Test
    @DisplayName("Debe rutear un Query a su handler SIN usar transacción")
    void shouldRouteQueryToHandlerWithoutTransaction() {
        var query = new GetUserQuery("123");
        var result = mediator.send(query);

        assertEquals("User Founded", result);
        verify(queryHandlerSpy).handle(query);
    }

    @Test
    @DisplayName("Debe lanzar excepción si no hay handler para el Command")
    void shouldThrowExceptionWhenCommandHandlerNotFound() {
        record UnhandledCommand() implements Command<String> {}
        var command = new UnhandledCommand();

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> mediator.send(command)
        );

        assertTrue(exception.getMessage().contains("No se encontró CommandHandler"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si no hay handler para el Query")
    void shouldThrowExceptionWhenQueryHandlerNotFound() {
        record UnhandledQuery() implements Query<String> {}
        var query = new UnhandledQuery();

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> mediator.send(query)
        );

        assertTrue(exception.getMessage().contains("No se encontró QueryHandler"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el request no es Command ni Query")
    void shouldThrowExceptionWhenRequestIsUnknown() {
        var unknown = new UnknownRequest();

        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> mediator.send(unknown)
        );

        assertEquals("El request debe implementar Command o Query de forma explícita", exception.getMessage());
    }
}
