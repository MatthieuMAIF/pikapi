package org.pokemon.pikapi.error;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.Tuple4;
import io.vavr.collection.Iterator;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Potential<T> implements Either<EventProcessErrors, T> {
    private final Either<EventProcessErrors, T> either;

    private Potential(Either<EventProcessErrors, T> either) {
        this.either = either;
    }

    public static <R> Potential<R> empty() {
        return Potential.result(null);
    }

    public static <R> Potential<R> create(Either<EventProcessErrors, R> e) {
        return new Potential<>(e);
    }

    public static <R> Potential<R> result(R value) {
        return new Potential<>(Either.right(value));
    }

    public static <R> Potential<R> error(IError err) {
        return new Potential<>(Either.left(EventProcessErrors.error(err)));
    }

    public static <R> Potential<R> error(IError err, List<String> params) {
        return new Potential<>(Either.left(EventProcessErrors.error(err, params)));
    }

    public static <R> Potential<R> error(EventProcessErrors errs) {
        return new Potential<>(Either.left(errs));
    }

    public static <R1, R2> Potential<Tuple2<R1, R2>> concat(Potential<R1> a, Potential<R2> b) {
        return concat(a, b, Tuple::of);
    }

    public static <R1, R2, R3> Potential<Tuple3<R1, R2, R3>> concat(Potential<R1> a, Potential<R2> b, Potential<R3> c) {
        return concat(concat(a, b, Tuple::of), c, (Tuple2<R1, R2> t, R3 other) -> Tuple.of(t._1, t._2, other));
    }

    public static <R1, R2, R3, R4> Potential<Tuple4<R1, R2, R3, R4>> concat(Potential<R1> a, Potential<R2> b, Potential<R3> c, Potential<R4> d) {
        return concat(concat(a, b, c), d, (Tuple3<R1, R2, R3> t, R4 other) -> Tuple.of(t._1, t._2, t._3, other));
    }

    private static <A, B, R> Potential<R> concat(Potential<A> a, Potential<B> b, BiFunction<A, B, R> mapper) {
        if (a.either.isLeft() && b.either.isLeft()) {
            return new Potential<>(Either.left(a.either.getLeft().copy().appendError(b.either.getLeft()).build()));
        }

        if (a.either.isLeft() && b.either.isRight()) {
            return new Potential<>(Either.left(a.either.getLeft()));
        }

        if (a.either.isRight() && b.either.isLeft()) {
            return new Potential<>(Either.left(b.either.getLeft()));
        }

        return result(mapper.apply(a.either.get(), b.either.get()));
    }


    public static <R> CompletionStage<Potential<R>> toFuture(Potential<CompletionStage<R>> p) {
        return p.map(future -> future.thenApply(Potential::result)).getOrElseGet(err -> CompletableFuture.completedFuture(Potential.error(err)));
    }

    public Either<EventProcessErrors, T> toEither() {
        return either;
    }

    public static <R> Future<Potential<R>> swapFuture(Potential<Future<R>> eitherFuture) {
        if (eitherFuture.isLeft()) {
            return Future.successful(
                    Potential.error(eitherFuture.either.getLeft()));
        }
        return eitherFuture.get().map(Potential::result);
    }

    public static <R> Future<Potential<R>> swapFutureFlatMap(Potential<Future<Potential<R>>> eitherFuture) {
        return Potential.swapFuture(eitherFuture).map(potential -> potential.flatMap(Function.identity()));
    }

    @Override
    public <L> Potential<L> map(Function<? super T, ? extends L> mapper) {
        return new Potential<>(either.map(mapper));
    }

    @Override
    public <L> Potential<L> flatMap(Function<? super T, ? extends Either<EventProcessErrors, ? extends L>> mapper) {
        Either<EventProcessErrors, L> result = either.flatMap(mapper);
        return new Potential<>(result.isRight() ? Either.right(result.get()) : Either.left(result.getLeft()));
    }

    @Override
    public T get() {
        return either.get();
    }

    @Override
    public EventProcessErrors getLeft() {
        return either.getLeft();
    }

    @Override
    public boolean isRight() {
        return either.isRight();
    }

    @Override
    public boolean isLeft() {
        return either.isLeft();
    }

    @Override
    public boolean isEmpty() {
        return either.isEmpty();
    }

    @Override
    public boolean isAsync() {
        return either.isAsync();
    }

    @Override
    public boolean isLazy() {
        return either.isLazy();
    }

    @Override
    public boolean isSingleValued() {
        return either.isSingleValued();
    }

    @Override
    public Potential<T> peek(Consumer<? super T> action) {
        return new Potential<>(either.peek(action));
    }

    @Override
    public String stringPrefix() {
        return either.stringPrefix();
    }

    @Override
    public Iterator<T> iterator() {
        return either.iterator();
    }
}
