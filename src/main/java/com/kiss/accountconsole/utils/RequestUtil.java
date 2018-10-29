package com.kiss.accountconsole.utils;

import javax.servlet.ServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RequestUtil {

    public static String getRequestBody (ServletRequest request) throws IOException {

        InputStream in = request.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int n = 0;

        while ((n = in.read(bytes)) != -1) {
            output.write(bytes, 0, n);
        }

        String requestBody = new String(output.toByteArray(), "utf-8");
        ThreadLocalUtil.setByte("requestBody",bytes);
        return requestBody;
    }

    public static void setRequestBody () {

    }
}
