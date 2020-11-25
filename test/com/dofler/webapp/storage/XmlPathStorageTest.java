package com.dofler.webapp.storage;


import com.dofler.webapp.storage.serializer.XmlStreamStrategy;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamStrategy()));
    }
}
