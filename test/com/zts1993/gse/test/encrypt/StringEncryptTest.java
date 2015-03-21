package com.zts1993.gse.test.encrypt;

import com.zts1993.gse.encrypt.StringEncrypt;

/**
 * Created by TianShuo on 2015/3/21.
 */
public class StringEncryptTest {

    public static void main(String args[]) {
        StringEncrypt stringEncrypt=new StringEncrypt("SHA-256");
        String s;
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod("MD5");
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod("SHA-1");
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod("SHA-512");
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);
    }
}
