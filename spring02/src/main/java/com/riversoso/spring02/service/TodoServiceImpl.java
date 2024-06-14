package com.riversoso.spring02.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riversoso.spring02.domain.Todo;
import com.riversoso.spring02.mapper.TodoMapper;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoMapper TodoMapper;

    @Override
    public List<Todo> getTodos() throws Exception {
       return TodoMapper.selectTodos();
    }

    @Override
    public Todo getTodo(int tno) throws Exception {
       return TodoMapper.selectTodo(tno);
    }
    
}
