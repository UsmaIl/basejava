package com.dofler.webapp.storage;


import com.dofler.webapp.storage.serializer.JsonStreamStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamStrategy()));
    }
}
