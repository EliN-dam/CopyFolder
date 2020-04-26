/* https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/file/Files.html
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/file/Path.html#resolve(java.nio.file.Path)
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/file/Path.html#relativize(java.nio.file.Path)
 * https://mkyong.com/java8/java-8-foreach-examples/
 */

package copyfolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author zelda
 */
public class CopyFolder {

    public CopyFolder(String source, String destination){
        try {
            Path src = Paths.get(source);
            Path dest = Paths.get(destination).resolve(src.getFileName());
            this.copy(src, dest);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void copy(Path source, Path destination) throws IOException {
        if (!Files.exists(destination))
            Files.createDirectories(destination);
        Files.walk(source).forEach(path -> {
            //System.out.println(source);
            //System.out.println(path);
            copyFile(path, destination.resolve(source.relativize(path)));
        });
    }
    
    public static void copyFile(Path source, Path destination) {
        try {
            if (Files.isDirectory(source)){
                if (!Files.exists(destination)){
                    Files.createDirectories(destination);
                }
            } else {
                Files.copy(source, destination, REPLACE_EXISTING);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new CopyFolder("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\MiDir", "G:\\Pendrive\\DAM");
    }   
}