package pl.competencyproject.model.csv;

import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class LibraryCSV {
    private List<String> listFilesCSV;
    private String fullFolderPath;
    private File director;
    private final String nameFolder = "csv";

    LibraryCSV(TypeOfUsedDatabase type) {
        setPathApplication(type);
        director = new File(fullFolderPath);
        if (!director.isDirectory()) {
            // exception
        } else {
            File[] files = director.listFiles();
            for (File file : files) {
                String name = file.getName();
                String[] nameS = name.split(".csv");
                listFilesCSV.add(nameS[0]);
            }
        }
    }

    public void setPathApplication(TypeOfUsedDatabase type ) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir"));
        if(type == TypeOfUsedDatabase.OnlineOrginalDatabase) {
            sb.append("\\src\\main\\resources\\" + nameFolder + "\\");
        }else{
            sb.append("\\src\\test\\resources\\" + nameFolder + "\\");
        }
        fullFolderPath = sb.toString();
        listFilesCSV = new LinkedList<>();
    }

    public List<String> getFilslist() {
        return this.listFilesCSV;
    }

    public String getFullFolderPath() {
        return this.fullFolderPath;
    }

    public String toString() {
        System.out.print("{ " + this.nameFolder + " } -> { ");
        StringBuilder sb = new StringBuilder();
        for (String name : this.listFilesCSV) {
            sb.append(" " + name + " ");
        }
        sb.append(" }");
        return sb.toString();
    }

    public boolean existFileCSVinFolder(String nameCSV) {
        for (String aListFilesCSV : listFilesCSV) {
            if (aListFilesCSV.equals(nameCSV)) return true;
        }
        return false;
    }
}
