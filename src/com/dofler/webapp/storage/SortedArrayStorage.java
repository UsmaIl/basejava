package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage  {
    @Override
    Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, iterator, searchKey);
    }

    @Override
    void insertElement(Resume resume, int index) {
        int insertIndex = Math.abs(index + 1);
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, iterator - insertIndex);
        storage[insertIndex] = resume;
    }

    @Override
    void deleteElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, iterator - index - 1);
    }
}
