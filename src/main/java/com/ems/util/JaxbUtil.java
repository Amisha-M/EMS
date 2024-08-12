package com.ems.util;

import jakarta.xml.bind.*;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

public class JaxbUtil {

    //
    private static JAXBContext getContext(Class<?> clazz) throws JAXBException {
        return JAXBContext.newInstance(clazz);
    }

    public static String toJson(Object object) throws JAXBException {
        StringWriter writer = new StringWriter();
        Marshaller marshaller = getContext(object.getClass()).createMarshaller();
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
        marshaller.marshal(object, writer);
        return writer.toString();
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JAXBException {
        StringReader reader = new StringReader(json);
        Unmarshaller unmarshaller = getContext(clazz).createUnmarshaller();
        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
        StreamSource jsonSource = new StreamSource(reader);
        JAXBElement<T> root = unmarshaller.unmarshal(jsonSource, clazz);
        return root.getValue();

    }

}
