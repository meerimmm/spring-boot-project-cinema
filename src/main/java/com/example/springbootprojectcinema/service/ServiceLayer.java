package com.example.springbootprojectcinema.service;

import java.util.List;

public interface ServiceLayer<T> {
    void save(T t);

    T findById(Long id);

    List<T> findAll();

    T update(Long id, T t);

    void deleteById(Long id);

}
