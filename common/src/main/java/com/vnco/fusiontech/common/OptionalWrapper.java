package com.vnco.fusiontech.common;


import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Deprecated
public class OptionalWrapper<T> {
    
    private Optional<T> optional;
    
    private OptionalWrapper(Optional<T> optional) {
        this.optional = optional;
    }
    
    public static <T> OptionalWrapper<T> of(Optional<T> optional) {
        return new OptionalWrapper<>(optional);
    }
    
    public static <T> OptionalWrapper<T> of(@NotNull T value) {
        return new OptionalWrapper<>(Optional.of(value));
    }
    
    public static <T> OptionalWrapper<T> ofNullable(T value) {
        return new OptionalWrapper<>(Optional.ofNullable(value));
    }
    
    public T get() {
        return optional.get();
    }
    
    public Optional<T> getOptional() {
        return optional;
    }
    
    public OptionalWrapper<? extends T> or(Supplier<? extends OptionalWrapper<? extends T>> supplier) {
        return optional.isPresent() ? this : supplier.get();
    }
    
    public T orElse(T fallback) {
        return optional.orElse(fallback);
    }
    
    public T orElseGet(Supplier<? extends T> supplier) {
        return optional.orElseGet(supplier);
    }
    
    public T orElseThrow() {
        return optional.orElseThrow();
    }
    
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        return optional.orElseThrow(exceptionSupplier);
    }
    
    public boolean isPresent() {
        return optional.isPresent();
    }
    
    public void ifPresent(Consumer<T> action) {
        optional.ifPresent(action);
    }
    
    public void ifPresentOrElse(Consumer<T> action, Runnable actionIfEmpty) {
        optional.ifPresentOrElse(action, actionIfEmpty);
    }
    
    public <X extends RuntimeException> void ifPresentOrThrow(Consumer<T> action,
                                                              Supplier<? extends X> exceptionSupplier
    ) throws X {
        optional.ifPresentOrElse(action, () -> {throw exceptionSupplier.get();});
    }
    
    public boolean isEmpty() {
        return optional.isEmpty();
    }
    
    public <X extends Throwable> OptionalWrapper<T> throwIfEmpty(Supplier<? extends X> exceptionSupplier) throws X {
        if (isEmpty()) throw exceptionSupplier.get();
        return this;
    }
    
    public <U> OptionalWrapper<U> map(Function<? super T, ? extends U> mapper) {
        return ofNullable(optional.map(mapper).orElse(null));
    }
    
    public <U> OptionalWrapper<U> flatMap(Function<? super T, ? extends Optional<? extends U>> mapper) {
        return ofNullable(optional.flatMap(mapper).orElse(null));
    }
    
    public OptionalWrapper<T> filter(Predicate<T> predicate) {
        optional = optional.filter(predicate);
        return this;
    }
    
    public <X extends Throwable> OptionalWrapper<T> throwIf(@NonNull Predicate<T> predicate,
                                                            @NonNull Supplier<? extends X> exceptionSupplier
    ) throws X {
        if (optional.filter(predicate).isPresent()) {
            throw exceptionSupplier.get();
        }
        return this;
    }
    
    public Stream<T> stream() {
        return optional.stream();
    }
    
    public OptionalWrapper<T> peek(@NonNull Consumer<T> consumer) {
        optional.ifPresent(consumer);
        return this;
    }
}
