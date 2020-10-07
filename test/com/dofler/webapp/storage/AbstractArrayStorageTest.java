package com.dofler.webapp.storage;

import com.dofler.webapp.exception.ExistStorageException;
import com.dofler.webapp.exception.NotExistStorageException;
import com.dofler.webapp.exception.StorageException;
import com.dofler.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
        storage.get(UUID_2);
    }

    @Test
    public void getAll() {
        Resume[] resumes = storage.getAll();
        assertEquals(storage.size(), resumes.length);
        assertEquals(UUID_1, resumes[0].getUuid());
        assertEquals(UUID_2, resumes[1].getUuid());
        assertEquals(UUID_3, resumes[2].getUuid());
    }

    @Test
    public void get() {
        assertEquals(UUID_1, storage.get(UUID_1).getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.get(UUID_1);
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid4"));
        assertEquals("uuid4", storage.get("uuid4").getUuid());
        assertEquals(4, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test
    public void update() {
        Resume testResume = new Resume(UUID_2);
        storage.update(testResume);
        assertSame(testResume, storage.get(UUID_2));
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            for (int i = 4; i <= 10_000; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

}