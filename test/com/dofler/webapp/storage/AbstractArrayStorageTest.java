package com.dofler.webapp.storage;

import com.dofler.webapp.exception.StorageException;
import com.dofler.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;


public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            for (int i = 4; i <= 10_000; i++) {
                storage.save(new Resume("Name" + 1));
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume("Overflow"));
    }

}