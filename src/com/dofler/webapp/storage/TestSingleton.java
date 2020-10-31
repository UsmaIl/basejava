package com.dofler.webapp.storage;

import com.dofler.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance = new TestSingleton();

    private static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {}

    public static void main(String[] args) {
        TestSingleton.getInstance().toString();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.ordinal());

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }

    }

    public enum Singleton {
        INSTANCE
    }
}
