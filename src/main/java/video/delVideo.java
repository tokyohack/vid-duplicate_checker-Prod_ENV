package video;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
public class delVideo {
    public delVideo(String args) {
        try {
            Files.deleteIfExists(Path.of(args));
            System.out.println("\nERRR_AND_DELETE_" + args);
            System.out.println("削除しました。");
        } catch (IOException e1) {
            System.err.println("\n" + e1 + "\nERRR_DONT_DELETE");
            System.out.println("削除できませんでした。");
        }
    }
}