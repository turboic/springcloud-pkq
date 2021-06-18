package com.turboic.cloud;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        try {
            testFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue( true );
    }



    private void testFile() throws IOException {
        InputStream inputStream = new FileInputStream("C:\\Users\\taiji\\Desktop\\999.jpg");
        OutputStream outputStream = new FileOutputStream("C:\\Users\\taiji\\Desktop\\222.jpg");
        int length = 0;
        byte [] b = new byte[204800];
        while((length = inputStream.read(b,0,b.length))!=-1){
            outputStream.write(b,0,length);
        }
        outputStream.close();
        inputStream.close();
    }


    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
}
