package ru.wwcompany.corparativerecorder;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

import ru.wwcompany.corparativerecorder.Service.TCPClient;

public class MainActivity extends AppCompatActivity {
    CallRecord callRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callRecord = new CallRecord.Builder(this)
                .setRecordFileName("Record_" + new java.text.SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new java.util.Date()))
                .setRecordDirName("CallRecord")
                .setRecordDirPath(Environment.getExternalStorageDirectory().getPath())
                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                .setShowPhoneNumber(true)
                .setShowSeed(true)
                .build();
        App.setCallRecord(callRecord);
        callRecord.startCallRecordService();
        callRecord.startCallReceiver();

        App.setContext(this);
        TCPClient tcpClient = new TCPClient();
        App.setTcpClient(tcpClient);
    }
}
