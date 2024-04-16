package org.azir.easywatermark.enums;

import org.azir.easywatermark.exception.FileTypeUnSupportException;

/**
 * @author Azir
 * @date 2023/02/19
 */
public enum FileTypeEnums {

    /**
     * png, jpeg/jpg, webp, bmp
     */
    IMAGE,

    /**
     * pdf
     */
    PDF,

    /**
     * word, excel, ppt
     */
    OFFICE;


    private static final byte[] PNG_BYTE = {(byte) 0x89, (byte) 0x50, (byte) 0x4e, (byte) 0x47,
            (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A};

    private static final byte[] JPG_JPEG_BYTE = {(byte) 0xFF, (byte) 0xD8};

    private static final byte[] WEBP_BYTE = {(byte) 'R', (byte) 'I', (byte) 'F', (byte) 'F'};

    private static final byte[] BMP_BYTE = {(byte) 'B', (byte) 'M'};

    private static final byte[] PDF_BYTE = {(byte) '%', (byte) 'P', (byte) 'D', (byte) 'F'};

    private static final byte[] OFFICE_BYTE = {(byte) 0xD0, (byte) 0xCF, (byte) 0x11, (byte) 0xE0,
            (byte) 0xA1, (byte) 0xB1, (byte) 0x1A, (byte) 0xE1};

    /**
     * Parse byte steam data type.
     *
     * @param date file byte data
     * @return file type
     */
    public static FileTypeEnums parseFileType(byte[] date) {
        if (isOffice(date)) {
            return OFFICE;
        }
        if (isPdf(date)) {
            return PDF;
        }
        if (isImage(date)) {
            return IMAGE;
        }
        throw new FileTypeUnSupportException("Byte data file is not support.");
    }

    private static boolean isOffice(byte[] data) {
        return isType(data, OFFICE_BYTE);
    }

    private static boolean isPdf(byte[] data) {
        return isType(data, PDF_BYTE);
    }

    public static boolean isImage(byte[] data) {
        return isType(data, PNG_BYTE) || isType(data, JPG_JPEG_BYTE)
                || isType(data, WEBP_BYTE) || isType(data, BMP_BYTE);
    }

    private static boolean isType(byte[] data, byte[] type) {
        if (data.length < type.length) {
            return false;
        }
        for (int i = 0; i < type.length; i++) {
            if (data[i] != type[i]) {
                return false;
            }
        }
        return true;
    }
}
