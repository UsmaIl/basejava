package com.dofler.webapp.util;


import com.dofler.webapp.model.AbstractSection;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class XmlParser {
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;
    private final StringWriter sw;

    public XmlParser(Class... classesToBeBound) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(classesToBeBound);

            marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            unmarshaller = ctx.createUnmarshaller();
            sw = new StringWriter();
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }


    public void marshall(Object instance, Writer writer) {
        try {
            marshaller.marshal(instance, writer);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> T unmarshall(String section) throws JAXBException {
        return (T) unmarshaller.unmarshal(marshaller.getNode(section));
    }

    public String marshall(Object instance) {
        try {
            marshaller.marshal(instance, sw);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
        return sw.toString();
    }

    public <T> T unmarshall(Reader reader) {
        try {
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }


}
