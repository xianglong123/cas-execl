package com.cas.utils;

@FunctionalInterface
public interface CustomConsumer<T, U, Z> {

    void accept(T t, U u, Z z);

}