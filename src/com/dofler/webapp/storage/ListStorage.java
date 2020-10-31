package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage<Integer> {
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
            if (Objects.equals(uuid, list.get(i).getUuid())) {
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
        list.add(r);
    }

    @Override
    void doDelete(Integer index) {
        list.remove(index.intValue());
    }

    @Override
    Resume doGet(Integer index) {
        return list.get(index);
    }

    @Override
    List<Resume> getAll() {
        return list;
    }

    @Override
    void doUpdate(Resume r, Integer index) {
        list.set(index, r);
    }
}
