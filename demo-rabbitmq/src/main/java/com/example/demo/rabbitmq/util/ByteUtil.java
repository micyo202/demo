package com.example.demo.rabbitmq.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Optional;

/**
 * ByteUtil
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/15
 */
public class ByteUtil {

    public static <T> Optional<T> bytesToObject(byte[] bytes) {
        T t = null;
        ByteArrayInputStream bIn = new ByteArrayInputStream(bytes);
        ObjectInputStream oIn;
        try {
            oIn = new ObjectInputStream(bIn);
            t = (T) oIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(t);
    }

}
