package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> uuidMap = new HashMap<>();

    @Override
    String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    boolean isExist(String uuid) {
        return uuidMap.containsKey(uuid);
    }

    @Override
    void save(Resume r, String uuid) {
        uuidMap.put(uuid, r);
    }

    @Override
    void doDelete(String uuid) {
        uuidMap.remove((uuid));
    }

    @Override
    Resume doGet(String uuid) {
        return uuidMap.get(uuid);
    }

    @Override
    List<Resume> getAll() {
        return new ArrayList<>(uuidMap.values());
    }

    @Override
    void doUpdate(Resume r, String uuid) {
        uuidMap.put(uuid, r);
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
