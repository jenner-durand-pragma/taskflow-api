package com.example.taskflowapi.infrastructure.mediator;

import com.example.taskflowapi.application.mediator.*;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpringMediator implements Mediator {

    private final Map<Class<? extends Command<?>>, CommandHandler<?, ?>> commandRegistry = new HashMap<>();
    private final Map<Class<? extends Query<?>>, QueryHandler<?, ?>> queryRegistry = new HashMap<>();

    private final TransactionTemplate transactionTemplate;

    @SuppressWarnings("unchecked")
    public SpringMediator(
            List<CommandHandler<?, ?>> commandHandlers,
            List<QueryHandler<?, ?>> queryHandlers,
            TransactionTemplate transactionTemplate) {

        this.transactionTemplate = transactionTemplate;

        for (var handler : commandHandlers) {
            var type = ResolvableType.forClass(CommandHandler.class, handler.getClass());
            var commandType = (Class<? extends Command<?>>) type.resolveGeneric(0);
            if (commandType != null) {
                commandRegistry.put(commandType, handler);
            }
        }

        for (var handler : queryHandlers) {
            var type = ResolvableType.forClass(QueryHandler.class, handler.getClass());
            var queryType = (Class<? extends Query<?>>) type.resolveGeneric(0);
            if (queryType != null) {
                queryRegistry.put(queryType, handler);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D, T extends Request<D>> D send(T request) {
        if (request instanceof Command<?> command) {
            return (D) handleCommand(command);
        }

        if (request instanceof Query<?> query) {
            return (D) handleQuery(query);
        }

        throw new IllegalArgumentException("El request debe implementar Command o Query de forma explícita");
    }

    @SuppressWarnings("unchecked")
    private <R, C extends Command<R>> R handleCommand(C command) {
        var handler = (CommandHandler<C, R>) commandRegistry.get(command.getClass());

        if (handler == null) {
            throw new IllegalArgumentException("No se encontró CommandHandler para: " + command.getClass().getName());
        }

        return transactionTemplate.execute(status -> handler.handle(command));
    }

    @SuppressWarnings("unchecked")
    private <R, Q extends Query<R>> R handleQuery(Q query) {
        var handler = (QueryHandler<Q, R>) queryRegistry.get(query.getClass());

        if (handler == null) {
            throw new IllegalArgumentException("No se encontró QueryHandler para: " + query.getClass().getName());
        }

        return handler.handle(query);
    }
}