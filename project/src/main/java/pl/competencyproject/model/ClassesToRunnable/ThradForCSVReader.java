package pl.competencyproject.model.ClassesToRunnable;

import pl.competencyproject.model.csv.CSVReader;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

public class ThradForCSVReader implements Runnable {

    private CSVReader csvReader;
    public ThradForCSVReader(TypeOfUsedDatabase type){csvReader = CSVReader.getInstance(type);}
    @Override
    public void run() {
       csvReader.initSentToDB();
       System.out.println("koniec pracy");
       Thread.currentThread().interrupt();
    }
}
