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
    int getIndex(String uuid) {
        return list.indexOf(new Resume(uuid));
    }

    @Override
    boolean isExist(int index) {
        return index >= 0;
    }

    @Override
    void save(Resume resume, int index) {
        list.add(resume);
    }

    @Override
    void delete(int index) {
        list.remove(index);
    }

    @Override
    Resume get(int index) {
        return list.get(index);
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    void update(Resume resume, int index) {
        list.set(index, resume);
    }
}
