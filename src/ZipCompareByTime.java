import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.*;

public class ZipCompareByTime {

    public static Map<String, File> chooseZipFiles(){
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Zip Files", "zip");
        fileChooser.setFileFilter(filter);

        fileChooser.setCurrentDirectory(new File("C:\\Users"));

        fileChooser.setMultiSelectionEnabled(true);

        int result = fileChooser.showOpenDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            File[] selectedFiles = fileChooser.getSelectedFiles();

            Map<String, File> mapOfZipFiles = new HashMap<>();

            for(File file : selectedFiles){
                mapOfZipFiles.put(file.getName(), file);
            }

            return mapOfZipFiles;
        }else{
            System.out.println("No files selected");
            return Collections.emptyMap();
        }
    }
    public static void compareTwoZips(File file1, File file2) {
        long date1 = file1.lastModified();
        long date2 = file2.lastModified();

        if(date1 != date2){
            System.out.println("Files: " + file1.getName() + " and " + file2.getName() + " Have different modification dates.");
        }
    }

    public static void compareListOfZips(){

        Map<String, File> map1 = chooseZipFiles();
        if(map1.isEmpty()) return;
        Map<String, File> map2 = chooseZipFiles();
        if(map2.isEmpty()) return;

        for (String key : map1.keySet()) {
            if (map2.containsKey(key)) {
                compareTwoZips(map1.get(key), map2.get(key));
            }
        }
    }

    public static void main(String[] args) {
        compareListOfZips();
    }
}