package com.hhit.hhble.util;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

@SuppressLint({"UseSparseArrays", "SimpleDateFormat", "DefaultLocale"})
public class Tools {
    public static final String         IS_CONNECTED                    = "isConnected";
    public static final String         PROPERTY_SIGNED_WRITE           = "authenticatedSignedWrites";
    public static final String         PROPERTY_BROADCAST              = "broadcast";
    public static final String         PROPERTY_EXTENDED_PROPS         = "extendedProperties";
    public static final String         PROPERTY_INDICATE               = "indicate";
    public static final String         PROPERTY_NOTIFY                 = "notify";
    public static final String         PROPERTY_READ                   = "read";
    public static final String         PROPERTY_WRITE                  = "write";
    public static final String         PROPERTY_WRITE_NO_RESPONSE      = "writeWithoutResponse";
    public static final String         SERVICE_UUIDS                   = "serviceUUIDs";
    public static final String         LOCAL_NAME                      = "localName";
    public static final String         TXPOWER_LEVEL                   = "txPowerLevel";
    public static final String         SERVICE_DATA                    = "serviceData";
    public static final String         MANUFACTURER_DATA               = "manufacturerData";
    public static final String         OVERFLOW_SERVICE_UUIDS          = "overflowServiceUUIDs";
    public static final String         SOLICITED_SERVICE_UUIDS         = "solicitedServiceUUIDs";
    private static HashMap<Integer, String> propertys = new HashMap<Integer, String>();
    static {
        propertys.put(1, PROPERTY_BROADCAST);
        propertys.put(2, PROPERTY_READ);
        propertys.put(4, PROPERTY_WRITE_NO_RESPONSE);
        propertys.put(8, PROPERTY_WRITE);
        propertys.put(16, PROPERTY_NOTIFY);
        propertys.put(32, PROPERTY_INDICATE);
        propertys.put(64, PROPERTY_SIGNED_WRITE);
        propertys.put(128, PROPERTY_EXTENDED_PROPS);
    }

    public static void addProperty(JSONObject obj, String key, Object value) {
        try {
            obj.put(key, value);
        } catch (JSONException e) {

        }
    }

    public static JSONObject decodeAdvData(byte[] advData) {
        JSONObject jsonAdvData = new JSONObject();
        if (advData == null || advData.length == 0) {
            return jsonAdvData;
        }
        JSONArray serviceUUIDs = new JSONArray();
        JSONArray solicitedServiceUUIDs = new JSONArray();
        JSONArray overflowServiceUUIDs = new JSONArray();
        byte[] advDatac = advData;
        boolean isOver = true;
        while (isOver) {
            if (advData == null || advData.length == 0) {
                break;
            }
            int dataLen = advData[0];
            if (dataLen == 0) {
                isOver = false;
                break;
            }
            byte[] allData = new byte[dataLen];
            for (int i = 0; i < allData.length; i++) {
                allData[i] = advData[i + 1];
            }
            byte[] type = {allData[0]};
            byte[] data = new byte[allData.length - 1];
            for (int i = 0; i < data.length; i++) {
                data[i] = allData[i + 1];
            }
            if ((0xff & type[0]) == 0x02) {
                byte[] mByte = new byte[data.length];
                for (int i = 0; i < mByte.length; i++) {
                    mByte[i] = data[data.length - i - 1];
                }
                serviceUUIDs.put(bytesToHexString(mByte));
            } else if ((0xff & type[0]) == 0x03) {
                int number = data.length / 2;
                for (int i = 0; i < number; i++) {
                    byte[] mByte = {data[i * 2], data[i * 2 + 1]};
                    serviceUUIDs.put(bytesToHexString(mByte));
                }
            } else if ((0xff & type[0]) == 0x04) {
                byte[] mByte = new byte[data.length];
                for (int i = 0; i < mByte.length; i++) {
                    mByte[i] = data[data.length - i - 1];
                }
                serviceUUIDs.put(bytesToHexString(mByte));
            } else if ((0xff & type[0]) == 0x05) {
                int number = data.length / 4;
                for (int i = 0; i < number; i++) {
                    byte[] mByte = {data[i * 4], data[i * 4 + 1],
                            data[i * 4 + 2], data[i * 4 + 3]};
                    serviceUUIDs.put(bytesToHexString(mByte));
                }
            } else if ((0xff & type[0]) == 0x06) {
                byte[] mByte = new byte[data.length];
                for (int i = 0; i < mByte.length; i++) {
                    mByte[i] = data[data.length - i - 1];
                }
                serviceUUIDs.put(bytesToHexString(mByte));
            } else if ((0xff & type[0]) == 0x07) {
                int number = data.length / 16;
                for (int i = 0; i < number; i++) {
                    byte[] mByte = {data[i * 16], data[i * 16 + 1],
                            data[i * 16 + 2], data[i * 16 + 3],
                            data[i * 16 + 4], data[i * 16 + 5],
                            data[i * 16 + 6], data[i * 16 + 7],
                            data[i * 16 + 8], data[i * 16 + 9],
                            data[i * 16 + 10], data[i * 16 + 11],
                            data[i * 16 + 12], data[i * 16 + 13],
                            data[i * 16 + 14], data[i * 16 + 15]};
                    serviceUUIDs.put(bytesToHexString(mByte));
                }
            } else if ((0xff & type[0]) == 0x08) {
                addProperty(jsonAdvData, LOCAL_NAME,
                        hexStrToStr(bytesToHexString(data)));
            } else if ((0xff & type[0]) == 0x09) {
                addProperty(jsonAdvData, LOCAL_NAME,
                        hexStrToStr(bytesToHexString(data)));
            } else if ((0xff & type[0]) == 0x0a) {
                addProperty(jsonAdvData, TXPOWER_LEVEL, bytesToHexString(data));
            } else if ((0xff & type[0]) == 0x12) {
                addProperty(jsonAdvData, IS_CONNECTED, bytesToHexString(data));
            } else if ((0xff & type[0]) == 0x14) {
                int number = data.length / 2;
                for (int i = 0; i < number; i++) {
                    byte[] mByte = {data[i * 2], data[i * 2 + 1]};
                    solicitedServiceUUIDs.put(bytesToHexString(mByte));
                }
            } else if ((0xff & type[0]) == 0x15) {
                int number = data.length / 16;
                for (int i = 0; i < number; i++) {
                    byte[] mByte = {data[i * 16], data[i * 16 + 1],
                            data[i * 16 + 2], data[i * 16 + 3],
                            data[i * 16 + 4], data[i * 16 + 5],
                            data[i * 16 + 6], data[i * 16 + 7],
                            data[i * 16 + 8], data[i * 16 + 9],
                            data[i * 16 + 10], data[i * 16 + 11],
                            data[i * 16 + 12], data[i * 16 + 13],
                            data[i * 16 + 14], data[i * 16 + 15]};
                    solicitedServiceUUIDs.put(bytesToHexString(mByte));
                }
            } else if ((0xff & type[0]) == 0x16) {
                addProperty(jsonAdvData, SERVICE_DATA, bytesToHexString(data));
            } else if ((0xff & type[0]) == 0xff) {
                addProperty(jsonAdvData, MANUFACTURER_DATA, bytesToHexString(data));
            }
            byte[] newData = new byte[advData.length - dataLen - 1];
            for (int i = 0; i < newData.length; i++) {
                newData[i] = advData[i + 1 + dataLen];
            }
            advData = newData;
        }
        addProperty(jsonAdvData, SERVICE_UUIDS, serviceUUIDs);
        addProperty(jsonAdvData, SOLICITED_SERVICE_UUIDS, solicitedServiceUUIDs);
        addProperty(jsonAdvData, OVERFLOW_SERVICE_UUIDS, overflowServiceUUIDs);
        addProperty(jsonAdvData, "advData", advDatac);
        return jsonAdvData;
    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String hexStrToStr(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }
}
