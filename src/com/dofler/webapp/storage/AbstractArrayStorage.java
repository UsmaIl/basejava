package com.dofler.webapp.storage;

import com.dofler.webapp.exception.StorageException;
import com.dofler.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

abstract class AbstractArrayStorage extends AbstractStorage<Integer>  {
    private static final int STORAGE_LIMIT = 10_000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int iterator = 0;

    abstract void insertElement(Resume r, int index);

    abstract void deleteElement(int index);

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
    public void save(Resume resume, Integer index) {
        if (iterator == storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(resume, index);
        iterator++;
    }

    @Override
    public void doDelete(Integer index) {
        deleteElement(index);
        storage[iterator - 1] = null;
        iterator--;
    }

    @Override
    public void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    public Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, iterator));
    }

    @Override
    boolean isExist(Integer index) {
        return  index >= 0;
    }
}
