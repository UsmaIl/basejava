package com.dofler.webapp.storage;

import com.dofler.webapp.exception.ExistStorageException;
import com.dofler.webapp.exception.NotExistStorageException;
import com.dofler.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    abstract int getIndex(String uuid);

    abstract boolean isExist(int index);

    abstract void save(Resume resume, int index);

    abstract void delete(int index);

    abstract Resume get(int index);

    abstract void update(Resume resume, int index);


    @Override
    public void update(Resume resume) {
        if (resume == null) {
            throw new NullPointerException();
        }
        update(resume, getExistedIndex(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        if (resume == null) {
            throw new NullPointerException();
        }
        save(resume, getNotExistedIndex(resume.getUuid()));
    }

    @Override
    public void delete(String uuid) {
        delete(getExistedIndex(uuid));
    }

    @Override
    public Resume get(String uuid) {
        return get(getExistedIndex(uuid));
    }

    private int getExistedIndex(String uuid) {
        int index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    private int getNotExistedIndex(String uuid) {
        int index = getIndex(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

}
