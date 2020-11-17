package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    Resume getSearchKey(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    void save(Resume r, Resume searchKey) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    void doDelete(Resume resume) {
        resumeMap.remove(resume.getUuid());
    }

    @Override
    Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    void doUpdate(Resume r, Resume resume) {
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
