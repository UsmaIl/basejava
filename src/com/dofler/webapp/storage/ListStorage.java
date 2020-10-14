package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void update(Resume resume) {
        list.set(getIndex(resume.getUuid()), resume);
    }

    @Override
    public void save(Resume resume) {
        list.add(resume);
    }

    @Override
    public void delete(String uuid) {
        list.remove(getIndex(uuid));
    }

    @Override
    public Resume get(String uuid) {
        return list.get(getIndex(uuid));
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return list.size();
    }

    private int getIndex(String uuid) {
        return list.indexOf(new Resume(uuid));
    }
}
