package com.cnr.cnrmodule.util;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SystemUtils {
    private static long lastClickTime;

    public static int getAppVersionCode(Context context) {
        int versionName = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    //获取运营商0-无 1-移动 2-联通 3-电信
    public static int getNetOp(Context context) {
        String type = getNetType(context);
        if (type.startsWith("移动")) {
            return 1;
        }
        if (type.startsWith("联通")) {
            return 2;
        }
        if (type.startsWith("电信")) {
            return 3;
        }
        return 0;
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取 imsi信息
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getImsi(Context context) {
        TelephonyManager telephonymanager = (TelephonyManager) context.getSystemService("phone");
        @SuppressLint({"HardwareIds", "MissingPermission"}) String mImsi = null;
        if (telephonymanager != null) {
            mImsi = telephonymanager.getSubscriberId();
        }
        if (mImsi == null || mImsi.equals("")) {
            mImsi = "10086";
        }
        return mImsi;
    }
//    private String getAndroidIMEI(Context context){
//        String androidIMEI = android.provider.Settings.System.getString(getContentResolver(context), android.provider.Settings.System.ANDROID_ID);
//        Log.d("ANDROID_ID", androidIMEI + " ");
//        return androidIMEI;
//    }

    /**
     * 获取imei信息
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        if (null == context)
            return "10086";
        TelephonyManager TelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint({"MissingPermission", "HardwareIds"}) String szImei = null;
        if (TelephonyMgr != null) {
            szImei = TelephonyMgr.getDeviceId();
        }
        if (szImei == null || szImei.equals(""))
            szImei = "10086";
        return szImei;
    }

    private static String getWlanMac(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String wlanMac = wm.getConnectionInfo().getMacAddress();
        return wlanMac;
    }

    private static String getBtMac() {
        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String btMac = m_BluetoothAdapter.getAddress();
        return btMac;
    }

    private static String getUuid() {
        String devIDShort = "35" +

                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
                + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length()
                % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
                + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length()
                % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10; // 13
        // digits
        return devIDShort;
    }

    private static String getAndroidId(Context context) {
        String androidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        return androidID;
    }

    // 获取到手机号码

    /**
     * sn唯一标识串号
     *
     * @param context
     * @return
     */
    public static String getMd5UniqueID(Context context) {

        String m_szLongID = getIMEI(context) + getUuid();
        // compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        // get md5 bytes
        byte p_md5Data[] = m.digest();
        // create a hex string
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper
            // padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
            // add number to string
            m_szUniqueID += Integer.toHexString(b);
        } // hex string to uppercase
        m_szUniqueID = m_szUniqueID.toUpperCase();
        return m_szUniqueID;
    }

    /**
     * hash channel
     *
     * @param context
     * @return
     */
    public static String getMd5Hash(Context context, String chn) {
        String m_szLongID = chn;
        // compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(m_szLongID)) {
            m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        }
        // get md5 bytes
        byte p_md5Data[] = m.digest();
        // create a hex string
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper
            // padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
            m_szUniqueID += Integer.toHexString(b);
        }

        m.update(m_szUniqueID.getBytes(), 0, m_szUniqueID.length());
        m_szUniqueID = "";
        // get md5 bytes
        byte p_md5Data1[] = m.digest();
        // create a hex string
        for (int i = 0; i < p_md5Data1.length; i++) {
            int b = (0xFF & p_md5Data1[i]);
            // if it is a single digit, make sure it have 0 in front (proper
            // padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
            m_szUniqueID += Integer.toHexString(b);
        }
        return m_szUniqueID;
    }

    // 获取到手机号码
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getMobileNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getLine1Number() : null;
    }

    // 获取运营商信息
    public static String getSimOperatorName(Context context) {
        String ctype = null;
        if (getMobileNumber(context) != null) {
            TelephonyManager telManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String operator = telManager.getSimOperator();
            if (operator != null) {
                if (operator.equals("46000") || operator.equals("46002")
                        || operator.equals("46007")) {
                    return ctype = "1";// 移动
                } else if (operator.equals("46001")) {
                    return ctype = "2";// 联通
                } else if (operator.equals("46003")) {
                    return ctype = "3";// 电信
                }
            }
        }

        return null;
    }

    public static void getId(Context context) {
        SystemUtils.getIMEI(context);
        SystemUtils.getImsi(context);
        SystemUtils.getAndroidId(context);
        SystemUtils.getUuid();
        SystemUtils.getWlanMac(context);
        SystemUtils.getBtMac();
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append(";");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    // 获取网络类型
    public static String getNetType(Context context) {
        /** 没有网络 */
        int NETWORKTYPE_INVALID = 0;
        String STR_NETWORKTYPE_INVALID = "无网络";
        /** wap网络 */
        int NETWORKTYPE_WAP = 1;
        /** 2G网络 */
        int NETWORKTYPE_2G = 2;
        String STR_YD_NETWORKTYPE_2G = "移动2G";
        String STR_LT_NETWORKTYPE_2G = "联通2G";
        /** 电信2G网络 */
        int DX_NETWORKTYPE_2G = 22;
        String STR_DX_NETWORKTYPE_2G = "电信2G";

        /** 3G和3G以上网络，或统称为快速网络 */
        int DX_NETWORKTYPE_3G = 3;
        String STR_DX_NETWORKTYPE_3G = "电信3G";
        /** 3G和3G以上网络，或统称为快速网络 */
        int LT_NETWORKTYPE_3G = 33;
        String STR_LT_NETWORKTYPE_3G = "联通3G";

        /** wifi网络 */
        int NETWORKTYPE_WIFI = 4;
        String STR_NETWORKTYPE_WIFI = "wifi";
        /** 其他网络 */
        int NET_TYPE = 5;
        String STR_NET_TYPE = "其他网络";
        ConnectivityManager cManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cManager.getActiveNetworkInfo();
        if (null == networkInfo) {
            // return NETWORKTYPE_INVALID;
            return STR_NETWORKTYPE_INVALID;
        } else {
            switch (networkInfo.getType()) {

                case ConnectivityManager.TYPE_WIFI: // wifi
                    NET_TYPE = NETWORKTYPE_WIFI;
                    STR_NET_TYPE = STR_NETWORKTYPE_WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE:// 手机网络
                    // * NETWORK_TYPE_CDMA 网络类型为CDMA
                    // * NETWORK_TYPE_EDGE 网络类型为EDGE
                    // * NETWORK_TYPE_EVDO_0 网络类型为EVDO0
                    // * NETWORK_TYPE_EVDO_A 网络类型为EVDOA
                    // * NETWORK_TYPE_GPRS 网络类型为GPRS
                    // * NETWORK_TYPE_HSDPA 网络类型为HSDPA
                    // * NETWORK_TYPE_HSPA 网络类型为HSPA
                    // * NETWORK_TYPE_HSUPA 网络类型为HSUPA
                    // * NETWORK_TYPE_UMTS 网络类型为UMTS
                    // 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，电信的3G为EVDO
                    switch (networkInfo.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                            NET_TYPE = DX_NETWORKTYPE_2G;
                            STR_NET_TYPE = STR_DX_NETWORKTYPE_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                            NET_TYPE = LT_NETWORKTYPE_3G;
                            STR_NET_TYPE = STR_LT_NETWORKTYPE_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                            STR_NET_TYPE = STR_YD_NETWORKTYPE_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                            NET_TYPE = NETWORKTYPE_2G;
                            STR_NET_TYPE = STR_LT_NETWORKTYPE_2G;
                            break;

                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                            NET_TYPE = DX_NETWORKTYPE_3G;
                            STR_NET_TYPE = STR_DX_NETWORKTYPE_3G;
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            return STR_NET_TYPE;
        }
    }

    public static String getdeviceInfo() {
        Build build = new Build();
        String str = Build.MODEL;
        return str;
    }

    public static String getMetaProjectId(Context context) {
        String msg = "0";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            msg = appInfo.metaData.getString("PROJECT_ID");
            // InitConfiguration.PUBLISH_CHANNEL = msg;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String getMetaAppId(Context context) {
        String msg = "0";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            msg = appInfo.metaData.getString("APP_ID");
            // InitConfiguration.PUBLISH_CHANNEL = msg;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String getGuid() {
        UUID uuid = UUID.randomUUID();
        String s = UUID.randomUUID().toString();
        return s;
    }

    /**
     * Get the screen width
     *
     * @param context
     * @return
     * @author mapeng_thun
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * Get the screen height
     *
     * @param context
     * @return
     * @author mapeng_thun
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 判断list是否有效
     */
    public static <T> boolean isListValidate(List<T> list) {
        return list != null && list.size() > 0;
    }

    public static int getAndroidOSVersion() {
        int osVersion;
        try {
            osVersion = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            osVersion = 0;
        }

        return osVersion;
    }

    static public String getCpuString() {
        if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
            return "Intel";
        }
        String strInfo = "";
        try {
            byte[] bs = new byte[1024];
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);

            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return strInfo;
    }

    static public String getCpuType() {
        String strInfo = getCpuString();
        String strType = null;
        if (strInfo.contains("ARMv5")) {
            strType = "armv5";
        } else if (strInfo.contains("ARMv6")) {
            strType = "armv6";
        } else if (strInfo.contains("ARMv7")) {
            strType = "armv7";
        } else if (strInfo.contains("Intel")) {
            strType = "x86";
        } else {
            strType = "unknown";
            return strType;
        }
        if (strInfo.contains("neon")) {
            strType += "_neon";
        } else if (strInfo.contains("vfpv3")) {
            strType += "_vfpv3";
        } else if (strInfo.contains(" vfp")) {
            strType += "_vfp";
        } else {
            strType += "_none";
        }
        return strType;
    }

    static public String getCpuType2() {
        String CPU_ABI = "";
        CPU_ABI = Build.CPU_ABI;
        return CPU_ABI;
    }

    /**
     * 获取请求的公共参数
     */
    public static HashMap<String, Object> getBaseParams(Context mContext) {
        //公共参数
//        String sn = SystemUtils.getMd5UniqueID(mContext);
//        String imei = SystemUtils.getIMEI(mContext);
//        String imsi = SystemUtils.getImsi(mContext);
        String sid = "3";
        int userId = 0;
        int userType = 0;
//        String tvUserId = UserTool.getTv_id(mContext);
        String tvUserId = "22";
        String tvUserName = "cnr";
        String platform = "0";
        String channelId = "CNRL010001";

        HashMap<String, Object> params = new HashMap<>();
//        params.put("stbId", sn);
        params.put("platform", platform);
        params.put("projectId", sid);
        params.put("channelId", channelId);
        params.put("tv_userId",tvUserId);
        params.put("tv_name",tvUserName);
//        params.put("imei", imei);
//        params.put("imsi", imsi);
        params.put("userId", userId + "");
        params.put("userType", userType + "");
        return params;
    }

    /**
     * 将 Map 转化为 String
     *
     * @param map 要转化的 Map
     * @return 转化后的 String
     */
    public static String transMapToString(Map map) {
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("=").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "&" : "");
        }
        return sb.toString();
    }
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                if (ipAddress == 0) return "未连接wifi";
                return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                        + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            return "当前无网络连接,请在设置中打开网络";
        }
        return getLocalIp();
    }

    /** * 得到有限网关的IP地址 * * @return */
    public static String getLocalIp() {

        try {
            // 获取本地设备的所有网络接口
            Enumeration<NetworkInterface> enumerationNi = NetworkInterface
                    .getNetworkInterfaces();
            while (enumerationNi.hasMoreElements()) {
                NetworkInterface networkInterface = enumerationNi.nextElement();
                String interfaceName = networkInterface.getDisplayName();
                Log.i("tag", "网络名字" + interfaceName);

                // 如果是有限网卡
                if (interfaceName.equals("eth0")) {
                    Enumeration<InetAddress> enumIpAddr = networkInterface
                            .getInetAddresses();

                    while (enumIpAddr.hasMoreElements()) {
                        // 返回枚举集合中的下一个IP地址信息
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        // 不是回环地址，并且是ipv4的地址
                        if (!inetAddress.isLoopbackAddress()
                                && inetAddress instanceof Inet4Address) {
                            Log.i("tag", inetAddress.getHostAddress() + " ");

                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";

    }

}
