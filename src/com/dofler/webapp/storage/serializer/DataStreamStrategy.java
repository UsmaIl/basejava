package com.dofler.webapp.storage.serializer;

import com.dofler.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@FunctionalInterface
interface WriterTypes<T> {
    void write(T t) throws IOException;
}

interface ReaderTypes<T> {
    T read() throws IOException;
}

public class DataStreamStrategy implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();

            dos.writeInt(contacts.size());

            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();

            dos.writeInt(sections.size());

            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                AbstractSection abstractSection = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) abstractSection).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeToList(dos, ((ListSection) abstractSection).getListSection(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Institution> list = ((ListInstitution) abstractSection).getListInstitution();
                        writeToList(dos, list, institution -> {
                            dos.writeUTF(institution.getHomePage().getName());
                            dos.writeUTF(institution.getHomePage().getUrl());
                            writeToList(dos, institution.getPlaces(), place -> {
                                writeLocalDate(dos, place.getStartDate());
                                writeLocalDate(dos, place.getEndDate());
                                dos.writeUTF(place.getTitle());
                                dos.writeUTF(place.getDescription());
                            });
                        });
                        break;
                }
            }
        }
    }

    private <T> void writeToList(DataOutputStream dos, List<T> list, WriterTypes<T> writer) throws IOException {
        dos.writeInt(list.size());
        for (T item : list) {
            writer.write(item);
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().getValue());
        dos.writeInt(ld.getDayOfMonth());
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
                        resume.addSection(sectionType, new ListSection(readList(dis, dis::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(sectionType, new ListInstitution(readList(dis,
                                () -> new Institution(new Link(dis.readUTF(), dis.readUTF()),
                                        readList(dis, () -> new Place(
                                                readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()))
                                ))));
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
            return resume;
        }
    }

    private <T> List<T> readList(DataInputStream dis, ReaderTypes<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }
}
