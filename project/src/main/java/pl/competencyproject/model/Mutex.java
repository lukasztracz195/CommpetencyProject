package pl.competencyproject.model;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class Mutex {


    public boolean lockInstance(final String lockFile) {
        try {
            final File file = new File(lockFile);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        try {
                            fileLock.release();
                            randomAccessFile.close();
                            file.delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                            file.delete();
                        }
                    }
                });
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }
}