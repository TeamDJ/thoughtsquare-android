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
    }

    public void delete(T obj) {
        db().delete(obj);
    }

    public List<T> findAll() {
        return db().query(clazz);
    }
}

