package com.dofler.webapp.storage;

import com.dofler.webapp.config.Config;
import com.dofler.webapp.exception.ExistStorageException;
import com.dofler.webapp.exception.NotExistStorageException;
import com.dofler.webapp.model.ContactType;
import com.dofler.webapp.model.Resume;
import com.dofler.webapp.ResumeTestData;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    static final File STORAGE_DIR = Config.getInstance().getStorageDir();

    final Storage storage;

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeTestData.createTestResume(UUID_1, "name1");
        RESUME_2 = ResumeTestData.createTestResume(UUID_2, "name2");
        RESUME_3 = ResumeTestData.createTestResume(UUID_3, "name3");
        RESUME_4 = ResumeTestData.createTestResume(UUID_4, "name4");
    }

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume testResume = new Resume(UUID_1, "Test Name");
        testResume.addContact(ContactType.PHONE_NUMBER, "+7(935) 142-4425");
        testResume.addContact(ContactType.MAIL, "lol@mail.ru");
        testResume.addContact(ContactType.MESSENGER, "Discord: TestName");
        storage.update(testResume);
        assertEquals(testResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.get(UUID_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dofler");
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storage.getAllSorted();
        assertEquals(3, actualResumes.size());
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), actualResumes);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dofler");
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}
