package com.dofler.webapp.storage;

import com.dofler.webapp.storage.serializer.ObjectStreamStrategy;

public class ObjectPathStorageTest extends AbstractStorageTest{
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStrategy()));
    }
}
