import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipCompareByTime {

    public static Map<String, ZipFile> chooseZipFiles() throws IOException{
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Zip Files", "zip");
        fileChooser.setFileFilter(filter);

        fileChooser.setCurrentDirectory(new File("C:\\Users\\Michał\\Documents\\My Games\\FarmingSimulator2022\\mods"));

        fileChooser.setMultiSelectionEnabled(true);

        int result = fileChooser.showOpenDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            File[] selectedFiles = fileChooser.getSelectedFiles();

            Map<String, ZipFile> mapOfZipFiles = new HashMap<>();

            for(File file : selectedFiles){
                mapOfZipFiles.put(file.getName(), new ZipFile(file));
            }

            return mapOfZipFiles;
        }else{
            System.out.println("nie wybrano pliku");
            return Collections.emptyMap();
        }
    }
    public static void compareTwoZips(ZipFile file1, ZipFile file2) throws IOException {

        List<? extends ZipEntry> entries1 = Collections.list(file1.entries());
        List<? extends ZipEntry> entries2 = Collections.list(file2.entries());

        Comparator<ZipEntry> comparatorByName = Comparator.comparing(ZipEntry::getName);
        entries1.sort(comparatorByName);
        entries2.sort(comparatorByName);

        Iterator<? extends ZipEntry> iterator1 = entries1.iterator();
        Iterator<? extends ZipEntry> iterator2 = entries2.iterator();

        while(iterator1.hasNext() && iterator2.hasNext()){
            ZipEntry entry1 = iterator1.next();
            ZipEntry entry2 = iterator2.next();

            if (entry1.getTime()!=entry2.getTime()){
                System.out.println("inny czas modyfikacji w pliku: " + file1.getName());
                return;
            }
        }
        return;
    }

    public static void compareListOfZips() throws IOException{

        Map<String, ZipFile> map1 = chooseZipFiles();
        Map<String, ZipFile> map2 = chooseZipFiles();

        if(map1.isEmpty() || map2.isEmpty()) return;

        if(map1.size()>map2.size()){
            for(String key : map2.keySet()){
                if(map1.containsKey(key)){
                    compareTwoZips(map1.get(key), map2.get(key));
                }
            }
        }else{
            for(String key : map1.keySet()){
                if(map2.containsKey(key)){
                    compareTwoZips(map1.get(key), map2.get(key));
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            compareListOfZips();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}