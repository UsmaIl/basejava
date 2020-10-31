package com.dofler.webapp.storage;

import com.dofler.webapp.exception.ExistStorageException;
import com.dofler.webapp.exception.NotExistStorageException;
import com.dofler.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    abstract SK getSearchKey(String uuid);

    abstract boolean isExist(SK searchKey);

    abstract void save(Resume r, SK searchKey);

    abstract void doDelete(SK searchKey);

    abstract Resume doGet(SK searchKey);

    abstract List<Resume> getAll();

    abstract void doUpdate(Resume r, SK searchKey);

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        if (resume != null) {
            doUpdate(resume, getExistedSearchKey(resume.getUuid()));
        }
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        if (resume != null) {
            save(resume, getNotExistedSearchKey(resume.getUuid()));
        }
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        doDelete(getExistedSearchKey(uuid));
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return doGet(getExistedSearchKey(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

}
