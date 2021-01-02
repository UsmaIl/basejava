package com.dofler.webapp.storage;

import com.dofler.webapp.Config;

public class SQLStorageTest extends AbstractStorageTest {
    public SQLStorageTest() {
        super(Config.getInstance().getStorage());
    }
}
