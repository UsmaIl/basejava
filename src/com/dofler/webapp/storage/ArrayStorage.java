package com.dofler.webapp.storage;


import com.dofler.webapp.model.Resume;

import java.util.stream.IntStream;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    int getIndex(String uuid) {
        return IntStream.range(0, iterator)
                .filter(i -> uuid.equals(storage[i].getUuid()))
                .findFirst()
                .orElse(-1);
    }

    @Override
    void insertElement(Resume r, int index) {
        storage[iterator] = r;
    }

    @Override
    void deleteElement(int index) {
        storage[index] = storage[iterator - 1];
    }
}
