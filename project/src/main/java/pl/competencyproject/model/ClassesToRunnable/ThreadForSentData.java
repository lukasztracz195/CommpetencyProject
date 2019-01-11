package pl.competencyproject.model.ClassesToRunnable;

import pl.competencyproject.model.csv.CSVReader;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

public class ThreadForSentData implements Runnable {

    private CSVReader csvReader;

    public ThreadForSentData(TypeOfUsedDatabase type) {
        csvReader = CSVReader.getInstance(type);
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        csvReader.initSentToDB();
        Thread.currentThread().interrupt();
    }
}


