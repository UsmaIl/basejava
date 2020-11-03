package com.dofler.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    private final String uuid;

    private final String fullName;

    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, Contentable> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName, Map<ContactType, String> contacts, Map<SectionType, Contentable> sections) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = contacts;
        this.sections = sections;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public Map<SectionType, Contentable> getSections() {
        return sections;
    }

    public void setSections(Map<SectionType, Contentable> sections) {
        this.sections = sections;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String AllToString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            str.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        str.append("\n");
        for (Map.Entry<SectionType, Contentable> entry : sections.entrySet()) {
            str.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return str.toString();
    }
    @Override
    public String toString() {
        return uuid + '[' + fullName + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
               Objects.equals(fullName, resume.fullName) &&
               Objects.equals(contacts, resume.contacts) &&
               Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public int compareTo(Resume r) {
        int result = fullName.compareTo(r.fullName);
        return result != 0 ? result : uuid.compareTo(r.uuid);
    }
}
