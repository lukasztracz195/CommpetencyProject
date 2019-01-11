package pl.competencyproject.model.ClassesToRunnable;

import pl.competencyproject.model.mechanicsOfQuestion.DictionaryMap;

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
