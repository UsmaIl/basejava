package com.dofler.webapp.storage;

import com.dofler.webapp.exception.StorageException;
import com.dofler.webapp.model.Resume;

import java.util.Arrays;

abstract class AbstractArrayStorage extends AbstractStorage  {
    private static final int STORAGE_LIMIT = 10_000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int iterator = 0;

    @Override
    public final int size() {
        return iterator;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, iterator, null);
        iterator = 0;
    }

    @Override
    public void save(Resume resume, Object index) {
        if (iterator == storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(resume, (int) index);
        iterator++;
    }

    @Override
    public void delete(Object index) {
        deleteElement((int) index);
        storage[iterator - 1] = null;
        iterator--;
    }

    @Override
    public void update(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    public Resume get(Object index) {
        return storage[(int) index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, iterator);
    }

    @Override
    boolean isExist(Object index) {
        return (int) index >= 0;
    }


    abstract void insertElement(Resume r, int index);

    abstract void deleteElement(int index);

}
