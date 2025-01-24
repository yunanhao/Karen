package com.project.base.viewadapter;

public interface BindingFunction<T, R> {

    R apply(T t);
}