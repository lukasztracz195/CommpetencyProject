package pl.competencyproject.model.Time;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GeneralClock {

    private volatile static GeneralClock instance;
    private Timeline timeline;

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    private String time;
    private String date;

    private GeneralClock() {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> setClock()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setClock() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        time = dtf.format((now));
        setDate();
    }

    private void setDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        date = dtf.format((now));
    }

    public static GeneralClock getInstance() {
        if (instance == null) {
            synchronized (GeneralClock.class) {
                if (instance == null) {
                    instance = new GeneralClock();
                }
            }
        }
        return instance;
    }


}
