package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.Arrays;

abstract class AbstractArrayStorage implements Storage{
    private static final int STORAGE_LIMIT = 10_000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int iterator = 0;

    public final int size() {
        return iterator;
    }

    public void clear() {
        Arrays.fill(storage, 0, iterator, null);
        iterator = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, iterator);
    }

    public final Resume get(String uuid) {
        if (uuid == null) {
            System.out.println("В метод get класса ArrayStorage передан null!!!");
            return null;
        }

        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Ошибка! Резюме с таким uuid: \"" + uuid + "\" не существует!");
            return null;
        }

        return storage[index];
    }

    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("В метод save класса Storage передан null!!!");
            return;
        }

        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index >= 0) {
            System.out.println("Ошибка! Резюме с таким uuid: \"" + uuid + "\" уже существует!");
            return;
        }

        if (iterator == storage.length) {
            System.out.println("Ошибка! Переполнение Storage");
            return;
        }

        insertElement(resume, index);
        iterator++;
    }

    public void delete(String uuid) {
        if (uuid == null) {
            System.out.println("В метод delete класса ArrayStorage передан null!!!");
            return;
        }

        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Ошибка! Резюме с таким uuid: \"" + uuid + "\" не существует!");
            return;
        }
        deleteElement(index);
        iterator--;
    }

    public void update(Resume resume) {
        if (resume == null) {
            System.out.println("В метод update класса ArrayStorage передан null!!!");
            return;
        }

        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Ошибка! Резюме с таким uuid: \"" + uuid + "\" не существует!");
            return;
        }

        storage[index] = resume;
    }

    public abstract int getIndex(String uuid);

    public abstract void insertElement(Resume r, int index);

    public abstract void deleteElement(int index);

}
