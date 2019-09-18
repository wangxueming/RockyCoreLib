package com.rockyw.locate;

import android.content.Context;

import com.rockyw.net.v2.Server;
import com.rockyw.projectcore.net.BaseResponse;
import com.rockyw.projectcore.net.CommonObserver;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 这是定位服务的SDK接口sdk
 *
 * @author: wangxueming
 * @version: 1.0.0
 * @date: 2018/12/14
 */
public class LocateHelper {

    private static final String AMAP_KEY = "c18a8f87d9ad698cd15567605bb03f53";

    /**
     * https://lbs.amap.com/api/webservice/guide/api/ipconfig/  利用ip定位
     *
     * @param context
     * @param listener
     */
    public static void requestLocation(Context context, ILocateCallback listener) {
        Server.getInstance().get3("https://restapi.amap.com/v3/ip?ip=" + getIP(context) + "&output=xml&key=" + AMAP_KEY, new CommonObserver<BaseResponse>(context) {
            @Override
            public void onNext(BaseResponse bankCardResponse) {

            }

            @Override
            public void onSuccess(BaseResponse bankCardResponse) {

            }
        });

    }

    public static String getIP(Context context) {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
