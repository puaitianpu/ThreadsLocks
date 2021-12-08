package com.tianpu.thread;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Downloader {

    private InputStream in;
    private OutputStream out;
    private ArrayList<ProgressListener> listeners;

    public Downloader(URL url, String outputFilename) throws IOException {
        in = url.openConnection().getInputStream();
        out = new FileOutputStream(outputFilename);
        listeners = new ArrayList<>();
    }

    public synchronized void addListener(ProgressListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeListener(ProgressListener listener) {
        listeners.remove(listener);
    }

    /*private synchronized void updateProgress(int n) {
        for (ProgressListener listener: listeners)
            listener.onProgress(n);      // 隐藏死锁陷阱，持有锁时调用外部的方法
    }*/

    private void updateProgress(int n) {
        ArrayList<ProgressListener> listenersCopy;
        synchronized (this) {
            listenersCopy = (ArrayList<ProgressListener>) listeners.clone();
        }
        for (ProgressListener listener: listenersCopy)
            listener.onProgress(n);
    }

    public void run() {
        int n , total = 0;
        byte[] buffer = new byte[1024];

        try {
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
                total += n;
                updateProgress(total);
            }
            out.flush();
        } catch (IOException e) { }
    }

}
