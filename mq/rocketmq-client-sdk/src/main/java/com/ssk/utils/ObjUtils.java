package com.ssk.utils;

import java.io.*;

/**
 * @author 惊云
 * @date 2021/12/13 14:57
 */
public class ObjUtils {

    public static Object byteToObj(byte [] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ObjectInputStream oInputStream = new ObjectInputStream(inputStream);
        return oInputStream.readObject();
    }

    public static byte[] objToByte(Object obj) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(obj);
        return outputStream.toByteArray();
    }

}
