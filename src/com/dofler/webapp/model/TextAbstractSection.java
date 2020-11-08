package com.dofler.webapp.model;

import java.util.Objects;

public class TextAbstractSection extends AbstractSection {
    private final String content;

    public TextAbstractSection(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextAbstractSection that = (TextAbstractSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
