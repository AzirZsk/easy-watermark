package org.easywatermark.utils;

import org.docx4j.vml.*;
import org.docx4j.vml.officedrawing.CTLock;
import org.docx4j.vml.wordprocessingDrawing.CTWrap;
import org.docx4j.wml.Pict;

import javax.xml.bind.JAXBElement;

/**
 * @author zhangshukun
 * @since 2024/07/02
 */
public class DocxUtils {

    /**
     * create JAXBElement
     *
     * @param value need package JAXBElement object
     * @param <T>
     * @return after package JAXBElement
     */
    @SuppressWarnings("all")
    public static <T> JAXBElement<T> createJAXBElement(T value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
        org.docx4j.vml.ObjectFactory vmlObjectFactory = new org.docx4j.vml.ObjectFactory();
        org.docx4j.vml.wordprocessingDrawing.ObjectFactory wpdObjectFactory = new org.docx4j.vml.wordprocessingDrawing.ObjectFactory();
        org.docx4j.vml.officedrawing.ObjectFactory odObjectFactory = new org.docx4j.vml.officedrawing.ObjectFactory();
        Class<?> clazz = value.getClass();
        if (clazz == Pict.class) {
            return (JAXBElement<T>) wmlObjectFactory.createRPict(((Pict) value));
        }
        if (clazz == CTShape.class) {
            return (JAXBElement<T>) vmlObjectFactory.createShape((CTShape) value);
        }
        if (clazz == CTWrap.class) {
            return (JAXBElement<T>) wpdObjectFactory.createWrap((CTWrap) value);
        }
        if (clazz == CTFill.class) {
            return (JAXBElement<T>) vmlObjectFactory.createFill((CTFill) value);
        }
        if (clazz == CTStroke.class) {
            return (JAXBElement<T>) vmlObjectFactory.createStroke((CTStroke) value);
        }
        if (clazz == CTLock.class) {
            return (JAXBElement<T>) odObjectFactory.createLock((CTLock) value);
        }
        if (clazz == CTTextPath.class) {
            return (JAXBElement<T>) vmlObjectFactory.createTextpath((CTTextPath) value);
        }
        // other type.....
        throw new UnsupportedOperationException("Unsupported value type: " + clazz.getName());
    }
}
