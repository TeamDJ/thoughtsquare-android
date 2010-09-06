package com.thoughtsquare.db;

import android.content.Context;

import java.io.Serializable;
import java.util.List;

public class DbProvider<T extends Serializable> extends Db4oHelper {
    public Class<T> clazz;

    public DbProvider(Class<T> clazz, Context context) {
        super(context);
        this.clazz = clazz;
    }

    public void store(T obj) {
        db().store(obj);
        db().commit();
    }

    public void delete(T obj) {
        db().delete(obj);
        db().commit();
    }

    public List<T> findAll() {
        return db().query(clazz);
    }

    public T get(T obj) {
        return (T) db().queryByExample(obj).next();
    }
}

