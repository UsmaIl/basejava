package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> resumes = new ArrayList<>();

    @Override
    public int size() {
        return resumes.size();
    }

    @Override
    public void clear() {
        resumes.clear();
    }

    @Override
    Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumes.size(); i++) {
            if (Objects.equals(uuid, resumes.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    void save(Resume r, Integer index) {
        resumes.add(r);
    }

    @Override
    void doDelete(Integer index) {
        resumes.remove(index.intValue());
    }

    @Override
    Resume doGet(Integer index) {
        return resumes.get(index);
    }

    @Override
    List<Resume> getAll() {
        return resumes;
    }

    @Override
    void doUpdate(Resume r, Integer index) {
        resumes.set(index, r);
    }
}
