package com.dofler.webapp.storage.serializer;

import com.dofler.webapp.model.*;
import com.dofler.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamStrategy implements SerializationStrategy {
    private XmlParser xmlParser;

    public XmlStreamStrategy() {
        xmlParser = new XmlParser(Resume.class, Institution.class, Link.class, ListInstitution.class,
                TextSection.class, ListSection.class, Place.class);
    }


    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
