package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int iterator = 0;

    public void clear() {
        Arrays.fill(storage, 0, iterator, null);
        iterator = 0;
    }

    private boolean contain(String uuid) {
        return Arrays.stream(storage, 0, iterator).noneMatch(id -> uuid.equals(id.getUuid()));
    }

    public void save(Resume resume) {
        if (iterator == storage.length) {
            System.out.println("Ошибка! Переполнение ArrayStorage");
            return;
        }

        if (resume == null) {
            System.out.println("В метод save класса ArrayStorage передан null!!!");
            return;
        }

        if (Arrays.asList(Arrays.copyOfRange(storage, 0, iterator)).contains(resume)) {
            System.out.println("Ошибка! Переданное резюме уже существует.");
            return;
        }
        storage[iterator++] = resume;
    }

    public Resume get(String uuid) {
        if (uuid == null) {
            System.out.println("В метод get класса ArrayStorage передан null!!!");
            return null;
        }

        if (this.contain(uuid)) {
            System.out.println("Ошибка! Резюме с таким uuid не существует!");
            return null;
        }

        for (int i = 0; i < iterator; i++) {
            if (Objects.equals(uuid, storage[i].toString())) {
                return storage[i];
            }
        }

        return null;
    }

    public void update(Resume resume) {
        if (resume == null) {
            System.out.println("В метод update класса ArrayStorage передан null!!!");
            return;
        }

        if (Arrays.asList(Arrays.copyOfRange(storage, 0, iterator)).contains(resume)) {
            System.out.println("Ошибка! Переданное резюме уже существует.");
            return;
        }

        storage[iterator - 1] = resume;
    }

    public void delete(String uuid) {
        if (uuid == null) {
            System.out.println("В метод delete класса ArrayStorage передан null!!!");
            return;
        }

        if (this.contain(uuid)) {
            System.out.println("Ошибка! Резюме с таким uuid не существует!");
            return;
        }

        for (int i = 0; i < iterator; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                storage[i] = storage[iterator - 1];
                storage[iterator - 1] = null;
                iterator--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, iterator);
    }

    public int size() {
        return iterator;
    }

}
