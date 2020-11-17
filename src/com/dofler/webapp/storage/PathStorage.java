package com.dofler.webapp.storage;

import com.dofler.webapp.exception.StorageException;
import com.dofler.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private SerializationProcess serializationProcess;

    PathStorage(String dir, SerializationProcess serializationProcess) {
        Objects.requireNonNull(dir, "directory must not be null");
        Objects.requireNonNull(dir, "serializationProcess must not be null");
        directory = Paths.get(dir);
        this.serializationProcess = serializationProcess;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
    }

    @Override
    void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Error deleting Path ", path.getFileName().toString());
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Error deleting Path ", null);
        }
    }

    @Override
    public int size() {
        int size = 0;
        try {
            size = (int) Files.list(directory).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
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
            throw new StorageException("Error creating Path ", path.getFileName().toString(), e);
        }
        doUpdate(r, path);
    }

    private void doWrite(Resume resume, Path path) throws IOException {
        serializationProcess.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
    }

    private Resume doRead(Path path) throws IOException {
        return serializationProcess.doRead(new BufferedInputStream(Files.newInputStream(path)));
    }


    @Override
    void doUpdate(Resume r, Path path) {
        try {
            doWrite(r, path);
        } catch (IOException e) {
            throw new StorageException("Error writing Path", r.getUuid(), e);
        }
    }

    @Override
    Resume doGet(Path path) {
        try {
            return doRead(path);
        } catch (IOException e) {
            throw new StorageException("Error reading Path", path.getFileName().toString(), e);
        }
    }

    @Override
    List<Resume> getAll() {
        List<Resume> resumes = null;
        try {
            resumes = Files.list(directory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resumes;
    }

    public void setSerializationProcess(SerializationProcess serializationProcess) {
        this.serializationProcess = serializationProcess;
    }
}
