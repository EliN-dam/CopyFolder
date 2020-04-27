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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        /*Iterator it = Files.walk(source).iterator();
        while (it.hasNext()){
            Path path = (Path)it.next();
            copyFile(path, destination.resolve(source.relativize(path)));
        }*/
        /*List<Path> files = Files.walk(source).collect(Collectors.toList());
        for (Path file : files){
            copyFile(file, destination.resolve(source.relativize(file)));
        }*/
        Files.walk(source).forEach(path -> {
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