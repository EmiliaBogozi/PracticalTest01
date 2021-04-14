package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.sql.Date;

public class ProcessingThread extends Thread {
    private Context context = null;
    private boolean isRunning = true;
    private double media_aritmetica, media_geometrica;

    public ProcessingThread(Context context, int a, int b) {
        this.context = context;
        media_aritmetica = (a + b) / 2;
        media_geometrica = Math.sqrt(a * b);
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "Thread has started! ");
        while(isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.INTENT_ACTION);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + media_aritmetica + " " + media_geometrica);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
