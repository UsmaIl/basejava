package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int iterator = 0;

    public void clear() {
        for (int i = 0; i < iterator; i++) {
            storage[i] = null;
        }
        iterator = 0;
    }

    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("В метод save класса com.dofler.webapp.storage.ArrayStorage передан null!!!");
            return;
        }
        storage[iterator++] = resume;
    }

    public Resume get(String uuid) {
        if (uuid == null) {
            System.out.println("В метод get класса com.dofler.webapp.storage.ArrayStorage передан null!!!");
        }

        for (int i = 0; i < iterator; i++) {
            if (Objects.equals(uuid, storage[i].toString())) {
                return storage[i];
            }
        }

        return null;
    }

    public void delete(String uuid) {
        if (uuid == null) {
            System.out.println("В метод delete класса com.dofler.webapp.storage.ArrayStorage передан null!!!");
            return;
        }

        for (int i = 0, j = 0; i < iterator; i++, j++) {
            if (uuid.equals(storage[i].toString())) {
                j++;
                iterator--;
            }
            storage[i] = storage[j];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[iterator];

        for (int i = 0; i < iterator; i++) {
            resumes[i] = storage[i];
        }

        return resumes;
    }

    public int size() {
        return iterator;
    }
}
