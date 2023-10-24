// 引数ファイルパスの確認
package video;

import java.io.File;

public class checkArgs {
    private String nameFilePath;

    public checkArgs(String[] args) {
        if (isArgsNull(args)) {
            String filePath = this.checkExistsFilePath(args[0]);
            if (filePath != null) {
                this.nameFilePath = filePath;
            }
        } else {
            System.out.println("引数が無いか2個以上入っています");
            System.exit(0);
        }
    }

    public String getterFilePath() {
        return this.nameFilePath;
    }

    //     引数が無い場合か引数が1以外の時Falseを返す
    public boolean isArgsNull(String[] args) {
        return args.length == 1 && args[0].trim().length() != 0 && !args[0].equals("\"\"") && !args[0].equals("\'\''") && !args[0].matches(".*\\*.*") && !args[0].equals("%PATH%");
    }

    public String checkExistsFilePath(String vidPath) {
        File fPath = new File(vidPath);
        if (fPath.exists() && !fPath.isDirectory()) {
            System.out.println("ファイルは存在します。");
            return vidPath;
        } else {
            System.err.println("ファイルがありません。");
            return null;
        }
    }
}
