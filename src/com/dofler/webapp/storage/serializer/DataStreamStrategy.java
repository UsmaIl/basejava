package com.dofler.webapp.storage.serializer;

import com.dofler.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamStrategy implements SerializationStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();

            dos.writeInt(contacts.size());

            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().getTitle());
                dos.writeUTF(entry.getValue());
            }

            dos.writeInt(contacts.size());

            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                AbstractSection abstractSection = entry.getValue();
                dos.writeUTF(sectionType.getTitle());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) abstractSection).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> sections = ((ListSection) abstractSection).getListSection();
                        dos.writeInt(sections.size());
                        for (String listSection : ((ListSection) abstractSection).getListSection()) {
                            dos.writeUTF(listSection);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Institution> institutions = ((ListInstitution) abstractSection).getListInstitution();
                        dos.writeInt(institutions.size());
                        for (Institution institution : institutions) {
                            dos.writeUTF(institution.getHomePage().getName());
                            dos.writeUTF(institution.getHomePage().getUrl());
                            for (Place place : institution.getPlaces()) {
                                dos.writeUTF(place.getStartDate().toString());
                                dos.writeUTF(place.getEndDate().toString());
                                dos.writeUTF(place.getTitle());
                                dos.writeUTF(place.getDescription());
                            }
                        }
                    }
                }

            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int sizeOfSections = dis.readInt();
                        List<String> sections = new ArrayList<>(sizeOfSections);
                        for (int j = 0; j < sizeOfSections; j++) {
                            sections.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(sections));

                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int sizeOfInstitutions = dis.readInt();
                        List<Institution> institutions = new ArrayList<>(sizeOfInstitutions);
                        for (int j = 0; j < sizeOfInstitutions; j++) {
                            List<Place> positions = institutions.get(j).getPlaces();
                            for (int k = 0; k < positions.size(); k++) {
                                positions.add(new Place(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()));
                            }
                            institutions.add(new Institution(new Link(dis.readUTF(), dis.readUTF()), positions));
                        }
                    default:
                        throw new IllegalStateException();
                }
            }
        }
        return null;
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }
}
