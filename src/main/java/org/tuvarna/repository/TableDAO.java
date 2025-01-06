package org.tuvarna.repository;

import java.util.List;

public interface TableDAO <T>{
    T findById(int id);
    List<T> findAll();
    T save(T t);
}
