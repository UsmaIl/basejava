package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    Resume getSearchKey(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    void save(Resume r, Object searchKey) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    void delete(Object resume) {
        resumeMap.remove(((Resume) resume).getUuid());
    }

    @Override
    Resume get(Object resume) {
        return (Resume) resume;
    }

    @Override
    void update(Resume r, Object resume) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public void update(Resume r) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    List<Resume> getAll() {
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
