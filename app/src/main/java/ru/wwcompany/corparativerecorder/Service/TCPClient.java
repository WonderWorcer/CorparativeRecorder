package ru.wwcompany.corparativerecorder.Service;

import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


/**
 * Created by Роман on 29.05.2017.
 */

public class TCPClient {
    private final static String SERVER = "192.168.0.9";
    private final static int FILE_SOCKET_PORT = 13267;
    private final static int FILENAME_SOCKET_PORT = 13268;

    public void sendFile(File filename) throws IOException {

        FileInputStream fis = null;
        OutputStream os = null;
        BufferedInputStream bis = null;


        Socket sock = null;
        Socket socket = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            sock = new Socket(SERVER, FILE_SOCKET_PORT);
            Log.i("TCPClient", "Connecting...");

            StrictMode.setThreadPolicy(policy);
            socket = new Socket(SERVER, FILENAME_SOCKET_PORT);
            Log.i("TCPClient", "Connecting... 2");


            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            // Send first message
            dOut.writeByte(1);
            dOut.writeUTF(filename.getName());
            dOut.flush(); // Send off the data
            // Send the exit message
            dOut.writeByte(-1);
            dOut.flush();
            dOut.close();


            os = sock.getOutputStream();
            byte[] mybytearray = new byte[(int) filename.length()];
            fis = new FileInputStream(filename);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            os = sock.getOutputStream();
            Log.i("TCPClient", "Sending " + filename + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();


            Log.i("TCPClient", "Done.");
        } finally {
            if (bis != null) bis.close();
            if (os != null) os.close();
            if (sock != null) sock.close();
        }
    }
}
