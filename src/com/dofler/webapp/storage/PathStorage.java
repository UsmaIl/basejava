package com.dofler.webapp.storage;

import com.dofler.webapp.exception.StorageException;
import com.dofler.webapp.model.Resume;
import com.dofler.webapp.storage.serializer.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializationStrategy serializationStrategy;

    PathStorage(String dir, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(dir, "directory must not be null");
        Objects.requireNonNull(dir, "serializationProcess must not be null");
        directory = Paths.get(dir);
        this.serializationStrategy = serializationStrategy;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
    }

    @Override
    void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Error deleting Path ", getFileNameString(path));
        }
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    @Override
    Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    void save(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Error creating Path ", getFileNameString(path), e);
        }
        doUpdate(r, path);
    }

    @Override
    void doUpdate(Resume r, Path path) {
        try {
            serializationStrategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Error writing Path", r.getUuid(), e);
        }
    }

    @Override
    Resume doGet(Path path) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Error reading Path", getFileNameString(path), e);
        }
    }

    @Override
    List<Resume> getAll() {
        return getFilesList().map(this::doGet).collect(Collectors.toList());
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }

    private String getFileNameString(Path path) {
        return path.getFileName().toString();
    }
}
