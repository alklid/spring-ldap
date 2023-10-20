package com.alklid.ldap.util;

public class LdapUtils {

    public static String decodeObjectGuid(byte[] objectGUID) {
        StringBuilder displayStr = new StringBuilder();

        displayStr.append(convertToDashedString(objectGUID));

        return displayStr.toString();
    }


    public static String decodeObjectSid(byte[] sid) {
        String strSID = "";
        int version;
        long authority;
        int count;
        String rid = "";
        strSID = "S";

        // get version
        version = sid[0];
        strSID = strSID + "-" + Integer.toString(version);
        for (int i=6; i>0; i--) {
            rid += byte2hex(sid[i]);
        }

        // get authority
        authority = Long.parseLong(rid);
        strSID = strSID + "-" + Long.toString(authority);

        //next byte is the count of sub-authorities
        count = sid[7]&0xFF;

        //iterate all the sub-auths
        for (int i=0;i<count;i++) {
            rid = "";
            for (int j=11; j>7; j--) {
                rid += byte2hex(sid[j+(i*4)]);
            }
            strSID = strSID + "-" + Long.parseLong(rid,16);
        }
        return strSID;
    }


    private static byte[] hexStringToByteArray(String hex) {
        int l = hex.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }


    private static String byte2hex(byte b) {
        String ret = Integer.toHexString((int)b&0xFF);
        if (ret.length()<2) ret = "0"+ret;
        return ret;
    }


    private static String convertToDashedString(byte[] objectGUID) {
        StringBuilder displayStr = new StringBuilder();

        displayStr.append(prefixZeros((int) objectGUID[3] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[2] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[1] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[0] & 0xFF));
        displayStr.append("-");
        displayStr.append(prefixZeros((int) objectGUID[5] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[4] & 0xFF));
        displayStr.append("-");
        displayStr.append(prefixZeros((int) objectGUID[7] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[6] & 0xFF));
        displayStr.append("-");
        displayStr.append(prefixZeros((int) objectGUID[8] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[9] & 0xFF));
        displayStr.append("-");
        displayStr.append(prefixZeros((int) objectGUID[10] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[11] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[12] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[13] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[14] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[15] & 0xFF));

        return displayStr.toString();
    }


    private static String prefixZeros(int value) {
        if (value <= 0xF) {
            StringBuilder sb = new StringBuilder("0");
            sb.append(Integer.toHexString(value));
            return sb.toString();
        } else {
            return Integer.toHexString(value);
        }
    }

}
