package com.demo.onvifcamera;


import org.apache.http.client.utils.URIBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class Util {

    //Depends on teh camera model
    private static String PATH = "/cam/realmonitor?channel=1&subtype=0";
    public static String getVIFString(String login, String password, String ip, int port)
    {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("rtsp");
        if(!login.isEmpty() && !password.isEmpty())
        {
            builder.setUserInfo(login, password);
        }
        else if(!login.isEmpty()) {
            builder.setUserInfo(login);
        }
        builder.setHost(ip);
        builder.setPort(port);
        builder.setPath(PATH);
        try {
            return java.net.URLDecoder.decode(builder.build().toString(), "UTF-8");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
