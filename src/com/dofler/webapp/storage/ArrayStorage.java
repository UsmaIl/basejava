package com.dofler.webapp.storage;

import com.dofler.webapp.model.Resume;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10_000;

    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int iterator = 0;

    public void clear() {
        Arrays.fill(storage, 0, iterator, null);
        iterator = 0;
    }

    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("В метод save класса ArrayStorage передан null!!!");
            return;
        }

        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index != -1) {
            System.out.println("Ошибка! Резюме с таким uuid: \"" + uuid + "\" не существует!");
            return;
        }

        if (iterator == storage.length) {
            System.out.println("Ошибка! Переполнение ArrayStorage");
            return;
        }

        storage[iterator++] = resume;
    }

    public Resume get(String uuid) {
        if (uuid == null) {
            System.out.println("В метод get класса ArrayStorage передан null!!!");
            return null;
        }

        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Ошибка! Резюме с таким uuid: \"" + uuid + "\" не существует!");
            return null;
        }

        return storage[index];
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

        storage[index] = storage[iterator - 1];
        storage[iterator - 1] = null;
        iterator--;
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

    private int getIndex(String uuid) {
        return IntStream.range(0, iterator)
                .filter(i -> uuid.equals(storage[i].getUuid()))
                .findFirst()
                .orElse(-1);
    }
}
