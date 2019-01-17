package pl.competencyproject.model.multithreadProcessing;

import pl.competencyproject.model.pollingMechanizm.DictionaryMap;

public class ThreadForDownloadData implements Runnable {

    private DictionaryMap dictionaryMap;

    public ThreadForDownloadData() {

        dictionaryMap = DictionaryMap.getInstance();

    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        dictionaryMap.initDownloadDate();
        Thread.currentThread().interrupt();
    }
}
