package com.riversoso.spring02.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.riversoso.spring02.*;

@Mapper
public interface TodoMapper {
    
    List <Todo> selectTodosAll();

    Todo selectTodo(int tno);
}
