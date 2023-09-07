package com.dmdevmvn.service.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
