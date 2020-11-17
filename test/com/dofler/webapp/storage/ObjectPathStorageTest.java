package com.dofler.webapp.storage;

public class ObjectPathStorageTest extends AbstractStorageTest{
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStorage()));
    }
}
