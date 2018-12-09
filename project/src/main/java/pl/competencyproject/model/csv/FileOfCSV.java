package pl.competencyproject.model.csv;

import lombok.Getter;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
@Getter
public class FileOfCSV {

    private String nameFile;
    private String fullPathFile;
    private File file;
    private Scanner read;

    public FileOfCSV(File file) throws FileNotFoundException {
        fullPathFile = file.getAbsolutePath();
        nameFile = file.getName();
           this.file = file;
            read = new Scanner(file,"UTF-8");
    }

    public FileOfCSV(String nameFile, TypeOfUsedDatabase type) throws FileNotFoundException {
        LibraryCSV library = new LibraryCSV(type);
        if(library.existFileCSVinFolder(nameFile)){
            createNameFile(nameFile);
            createFullPathFile(library,nameFile);
            file = new File(fullPathFile);
            read = new Scanner(file,"UTF-8");
        }
    }

    private void createFullPathFile(LibraryCSV library, String nameFile){
        StringBuilder sb = new StringBuilder();
        sb.append(library.getFullFolderPath()+"\\"+nameFile+".csv");
        fullPathFile = sb.toString();
    }

    private void createNameFile(String nameFile){
        StringBuilder sb = new StringBuilder();
        sb.append(nameFile).append(".csv");
        nameFile = sb.toString();

    }
}
