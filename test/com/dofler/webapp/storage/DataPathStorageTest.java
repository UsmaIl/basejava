package com.dofler.webapp.storage;


import com.dofler.webapp.storage.serializer.DataStreamStrategy;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamStrategy()));
    }
}
