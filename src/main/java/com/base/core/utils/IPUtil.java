package com.base.core.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author hebing
 * @date 2019-01-14
 **/

public class IPUtil {

    public static String getSerIp(){
        String serIp ="";
        // 根据网卡取本机配置的IP
        Enumeration<NetworkInterface> allNetInterfaces;  //定义网络接口枚举类
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();  //获得网络接口
            InetAddress ip = null; //声明一个InetAddress类型ip地址
            while (allNetInterfaces.hasMoreElements()) //遍历所有的网络接口
            {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses(); //同样再定义网络地址枚举类
                while (addresses.hasMoreElements())
                {
                    ip = addresses.nextElement();
                    if (ip != null && (ip instanceof Inet4Address)) //InetAddress类包括Inet4Address和Inet6Address
                    {
                        if(!ip.getHostAddress().equals("127.0.0.1")){
                            serIp= ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return serIp;
    }

    public static String getClientIp(HttpServletRequest request) {
        String accessIP = request.getHeader("X-Forwarded-For");
        if (accessIP == null || accessIP.length() == 0 || "unknown".equalsIgnoreCase(accessIP)) {
            accessIP = request.getHeader("Proxy-Client-IP");
        }
        if (accessIP == null || accessIP.length() == 0 || "unknown".equalsIgnoreCase(accessIP)) {
            accessIP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (accessIP == null || accessIP.length() == 0 || "unknown".equalsIgnoreCase(accessIP)) {
            accessIP = request.getRemoteAddr();
        }
        if (accessIP == null || accessIP.length() == 0 || "127.0.0.1".equalsIgnoreCase(accessIP)) {
            try {
                accessIP = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                accessIP = "";
            }
        }
        return accessIP;
    }
}
