package com.riversoso.spring02.service;

import java.util.List;

import com.riversoso.spring02.domain.Todo;

public interface TodoService {
    public List<Todo> getTodos() throws Exception;

    public Todo getTodo(int tno) throws Exception;
}
