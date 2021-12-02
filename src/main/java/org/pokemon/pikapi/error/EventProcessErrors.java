package org.pokemon.pikapi.error;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventProcessErrors {

    public final List<EventProcessError> errors;

    public EventProcessErrors() {
        this(newBuilder());
    }

    public EventProcessErrors(List<EventProcessError> errors) {
        this.errors = errors;
    }

    public EventProcessErrors(Builder builder) {
        this.errors = builder.errors;
    }

    public static EventProcessErrors error(IError e) {
        return new EventProcessErrors(List.of(EventProcessError.create(e)));
    }

    public static EventProcessErrors error(IError e, List<String> params) {
        return new EventProcessErrors(List.of(EventProcessError.create(e, params)));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Optional<EventProcessErrors> emptyEventProcessErrors() {
        return Optional.empty();
    }

    public Builder copy() {
        return newBuilder(this);
    }

    public static Builder newBuilder(EventProcessErrors copy) {
        Builder builder = new Builder();
        builder.errors = copy.errors;
        return builder;
    }

    public static final class Builder {
        private List<EventProcessError> errors;

        private Builder() {
        }

        public Builder appendError(EventProcessErrors eventProcessErrors) {
            this.errors = Stream.concat(
                            this.errors.stream(),
                            Optional.ofNullable(eventProcessErrors)
                                    .map(e -> e.errors)
                                    .orElseGet(Collections::emptyList)
                                    .stream())
                    .collect(Collectors.toList());
            return this;
        }


        public EventProcessErrors build() {
            return new EventProcessErrors(this);
        }
    }

}
