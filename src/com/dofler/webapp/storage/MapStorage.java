package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage implements Storage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void update(Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    public void save(Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    public Resume get(String uuid) {
        return map.get(uuid);
    }

    @Override
    public void delete(String uuid) {
        map.remove(uuid);
    }

    @Override
    public void clear() {
        map.clear();
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
