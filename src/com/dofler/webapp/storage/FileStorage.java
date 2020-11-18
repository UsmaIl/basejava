package com.dofler.webapp.storage;

import com.dofler.webapp.exception.StorageException;
import com.dofler.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private final SerializationProcess serializationProcess;

    FileStorage(File directory, SerializationProcess serializationProcess) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serializationProcess, "serializationProcess must not be null");
        this.directory = directory;
        this.serializationProcess = serializationProcess;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Error deleting file", file.getName());
        }
    }

    @Override
    public void clear() {
        File[] files = getFilesList();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        File[] files = getFilesList();
        if (files != null) {
            return files.length;
        }
        return 0;
    }

    @Override
    File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    boolean isExist(File file) {
        return file.exists();
    }

    @Override
    void save(Resume r, File file) {
        try {
            file.createNewFile();
            doUpdate(r, file);
        } catch (IOException e) {
            throw new StorageException("Error creating file ", file.getName(), e);
        }
    }

    @Override
    void doUpdate(Resume r, File file) {
        try {
            serializationProcess.doWrite(r, new BufferedOutputStream((new FileOutputStream(file))));
        } catch (IOException e) {
            throw new StorageException("Error writing file", r.getUuid(), e);
        }
    }

    @Override
    Resume doGet(File file) {
        try {
            return serializationProcess.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Error reading file", file.getName(), e);
        }
    }

    @Override
    List<Resume> getAll() {
        File[] files = getFilesList();
        if (files == null) {
            throw new StorageException("Error reading directory", directory.getName());
        }

        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    private File[] getFilesList() {
        return directory.listFiles();
    }
}
