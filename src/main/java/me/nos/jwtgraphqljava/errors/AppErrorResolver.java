package me.nos.jwtgraphqljava.errors;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;

//@Component
public class AppErrorResolver extends DataFetcherExceptionResolverAdapter {

    private final AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();

    @Override
    protected GraphQLError resolveToSingleError(@NotNull Throwable ex, @NotNull DataFetchingEnvironment env) {
        if (ex instanceof AuthenticationException) {
            return this.buildError(ex, env, ErrorType.UNAUTHORIZED);
        } else if (ex instanceof AccessDeniedException) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            return this.resolver.isAnonymous(securityContext.getAuthentication())
                    ? this.buildError(ex, env, ErrorType.UNAUTHORIZED)
                    : this.buildError(ex, env, ErrorType.FORBIDDEN);
        } else if (ex instanceof ConstraintViolationException) {
            return this.buildError(ex, env, ErrorType.BAD_REQUEST);
        } else {
            return null;
//            return this.buildError(ex, env, ErrorType.INTERNAL_ERROR);
        }
    }

    private GraphQLError buildError(Throwable ex, DataFetchingEnvironment env, ErrorType type) {
        return GraphqlErrorBuilder.newError(env).errorType(type).message(ex.getMessage(), new Object[0]).build();
    }
}
