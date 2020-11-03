package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> actualResumes = new ArrayList<>();

    @Override
    public int size() {
        return actualResumes.size();
    }

    @Override
    public void clear() {
        actualResumes.clear();
    }

    @Override
    Integer getSearchKey(String uuid) {
        for (int i = 0; i < actualResumes.size(); i++) {
            if (Objects.equals(uuid, actualResumes.get(i).getUuid())) {
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
        actualResumes.add(r);
    }

    @Override
    void doDelete(Integer index) {
        actualResumes.remove(index.intValue());
    }

    @Override
    Resume doGet(Integer index) {
        return actualResumes.get(index);
    }

    @Override
    List<Resume> getAll() {
        return actualResumes;
    }

    @Override
    void doUpdate(Resume r, Integer index) {
        actualResumes.set(index, r);
    }
}
