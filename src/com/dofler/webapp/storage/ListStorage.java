package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    Object getSearchKey(String uuid) {
        return list.indexOf(new Resume(uuid));
    }

    @Override
    boolean isExist(Object index) {
        return (int) index >= 0;
    }

    @Override
    void save(Resume r, Object index) {
        list.add(r);
    }

    @Override
    void delete(Object index) {
        list.remove((int) index);
    }

    @Override
    Resume get(Object index) {
        return list.get((int) index);
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    void update(Resume r, Object index) {
        list.set((int) index, r);
    }
}
