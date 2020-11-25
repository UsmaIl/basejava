package com.dofler.webapp.storage;

import com.dofler.webapp.storage.serializer.ObjectStreamStrategy;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}
