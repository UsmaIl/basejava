package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if(Objects.equals(uuid, list.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
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
    List<Resume> getAll() {
        return list;
    }

    @Override
    void update(Resume r, Object index) {
        list.set((int) index, r);
    }
}
