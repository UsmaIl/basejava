package com.dofler.webapp.storage.serializer;

import com.dofler.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamStrategy implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeToCollection(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeToCollection(dos, r.getSections().entrySet(), entry -> {
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
                        writeToCollection(dos, ((ListSection) abstractSection).getListSection(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Institution> list = ((ListInstitution) abstractSection).getListInstitution();
                        writeToCollection(dos, list, institution -> {
                            dos.writeUTF(institution.getHomePage().getName());
                            String url = institution.getHomePage().getUrl();
                            dos.writeUTF(url == null ? "" : url);
                            writeToCollection(dos, institution.getPlaces(), place -> {
                                writeLocalDate(dos, place.getStartDate());
                                writeLocalDate(dos, place.getEndDate());
                                dos.writeUTF(place.getTitle());
                                String description = place.getDescription();
                                dos.writeUTF(description == null ? "" : description);
                            });
                        });
                        break;
                    default:
                        throw new IllegalStateException();
                }
            });
        }
    }

    private <T> void writeToCollection(DataOutputStream dos, Collection<T> collection,
                                       Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
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
            readElements(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readElements(dis, () -> {
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
            });
            return resume;
        }
    }

    private void readElements(DataInputStream dis, VoidReader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private <T> List<T> readList(DataInputStream dis, Reader<T> reader) throws IOException {
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

    @FunctionalInterface
    interface Writer<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    interface Reader<T> {
        T read() throws IOException;
    }

    @FunctionalInterface
    interface VoidReader {
        void read() throws IOException;
    }
}
