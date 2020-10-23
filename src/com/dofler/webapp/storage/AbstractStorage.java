package com.dofler.webapp.storage;

import com.dofler.webapp.exception.ExistStorageException;
import com.dofler.webapp.exception.NotExistStorageException;
import com.dofler.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    abstract Object getSearchKey(String uuid);

    abstract boolean isExist(Object searchKey);

    abstract void save(Resume r, Object searchKey);

    abstract void delete(Object searchKey);

    abstract Resume get(Object searchKey);

    abstract List<Resume> getAll();

    abstract void update(Resume r, Object searchKey);

    @Override
    public void update(Resume resume) {
        if (resume != null) {
            update(resume, getExistedSearchKey(resume.getUuid()));
        }
    }

    @Override
    public void save(Resume resume) {
        if (resume != null) {
            save(resume, getNotExistedSearchKey(resume.getUuid()));
        }
    }

    @Override
    public void delete(String uuid) {
        delete(getExistedSearchKey(uuid));
    }

    @Override
    public Resume get(String uuid) {
        return get(getExistedSearchKey(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

}
