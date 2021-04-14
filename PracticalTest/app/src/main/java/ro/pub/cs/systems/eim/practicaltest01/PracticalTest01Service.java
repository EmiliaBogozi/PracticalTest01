package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Service extends Service {

    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int nr1 = intent.getIntExtra(Constants.FIRST_NUMBER, -1);
        int nr2 = intent.getIntExtra(Constants.SECOND_NUMBER, -1);
        processingThread = new ProcessingThread(this, nr1, nr2);
        processingThread.start();

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}