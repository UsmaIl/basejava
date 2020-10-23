package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> uuidMap = new HashMap<>();

    @Override
    Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    boolean isExist(Object uuid) {
        return uuidMap.containsKey((String) uuid);
    }

    @Override
    void save(Resume r, Object uuid) {
        uuidMap.put((String) uuid, r);
    }

    @Override
    void delete(Object uuid) {
        uuidMap.remove((String) uuid);
    }

    @Override
    Resume get(Object uuid) {
        return uuidMap.get((String) uuid);
    }

    @Override
    List<Resume> getAll() {
        return new ArrayList<>(uuidMap.values());
    }

    @Override
    void update(Resume r, Object searchKey) {

    }

    @Override
    public void clear() {
        uuidMap.clear();
    }

    @Override
    public int size() {
        return uuidMap.size();
    }
}
