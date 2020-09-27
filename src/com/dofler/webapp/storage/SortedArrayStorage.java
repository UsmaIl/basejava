package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage  {
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, iterator, searchKey);
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        int insertIndex = Math.abs(index + 1);
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, iterator - insertIndex);
        storage[insertIndex] = resume;
    }
}
