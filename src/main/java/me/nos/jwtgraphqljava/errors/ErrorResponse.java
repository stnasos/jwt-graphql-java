package me.nos.jwtgraphqljava.errors;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class ErrorResponse extends RuntimeException implements GraphQLError {
    private final String message;

    public ErrorResponse(String message) {
        //super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return new ErrorClassification() {
            @Override
            public Object toSpecification(GraphQLError error) {
                return ErrorClassification.super.toSpecification(error);
            }
        };
    }
}