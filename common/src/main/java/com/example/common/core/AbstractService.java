package com.example.common.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;
import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * AbstractService  Implement By MyBatis
 * <p>
 * 基于通用Mapper的MyBatis来实现Service接口的抽象Service
 */

public abstract class AbstractService<T> implements BaseService<T> {

    @Autowired
    protected Mapper mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public void save(T model) {
        mapper.insertSelective(model);
    }

    public void save(List<T> models) {
        mapper.insertList(models);
    }

    public void deleteById(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }


    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    public T findById(Integer id) {
        return (T) mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String property, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(property);
            field.setAccessible(true);
            field.set(model, value);
            return (T) mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new TooManyResultsException(e.getMessage(), e);
        }
    }


    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return mapper.selectAll();
    }


}