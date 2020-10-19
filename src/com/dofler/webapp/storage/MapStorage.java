package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    Object getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    void save(Resume r, Object searchKey) {
        map.put(r.getUuid(), r);
    }

    @Override
    void delete(Object resume) {
        map.remove(((Resume) resume).getUuid());
    }

    @Override
    Resume get(Object resume) {
        return (Resume) resume;
    }

    @Override
    void update(Resume r, Object resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void update(Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
