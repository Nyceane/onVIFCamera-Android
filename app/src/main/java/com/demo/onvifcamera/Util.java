package com.demo.onvifcamera;


import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import org.apache.http.client.utils.URIBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class Util {

    //Depends on teh camera model
    public static String getVIFString(String login, String password, String ip, String port)
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
        if(!port.isEmpty())
        {
            builder.setPort(Integer.valueOf(port));
        }
        try {
            return java.net.URLDecoder.decode(builder.build().toString(), "UTF-8");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getVIFUrlString(String ip, String port)
    {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("rtsp");
        builder.setHost(ip);
        if(!port.isEmpty())
        {
            builder.setPort(Integer.valueOf(port));
        }
        try {
            return java.net.URLDecoder.decode(builder.build().toString(), "UTF-8");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getMediaTime(int ms) {
        int hour, mintue, second;

        //计算小时 1 h = 3600000 ms
        hour = ms / 3600000;

        //计算分钟 1 min = 60000 ms
        mintue = (ms - hour * 3600000) / 60000;

        //计算秒钟 1 s = 1000 ms
        second = (ms - hour * 3600000 - mintue * 60000) / 1000;

        //格式化输出，补零操作
        String sHour, sMintue, sSecond;
        if (hour < 10) {
            sHour = "0" + String.valueOf(hour);
        } else {
            sHour = String.valueOf(hour);
        }

        if (mintue < 10) {
            sMintue = "0" + String.valueOf(mintue);
        } else {
            sMintue = String.valueOf(mintue);
        }

        if (second < 10) {
            sSecond = "0" + String.valueOf(second);
        } else {
            sSecond = String.valueOf(second);
        }

        return sHour + ":" + sMintue + ":" + sSecond;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
