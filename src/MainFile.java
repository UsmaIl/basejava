import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/dofler/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }


        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getListFiles(dir, 0);
    }

    public static void getListFiles(File file, int offset) {
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.println(file.getName());
        if (file.isDirectory()) {
            offset++;
        }
        if (file.isDirectory()) {
            String[] listOfFilesOrDirectories = file.list();
            if (listOfFilesOrDirectories != null) {
                for (String fileOrDirectory : listOfFilesOrDirectories) {
                    getListFiles(new File(file, fileOrDirectory), offset);
                }
            }
        }
    }
}
