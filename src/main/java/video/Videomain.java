// Main処理
package video;

import java.io.IOException;
import java.util.Map;

public class Videomain {
//    private Vidpathname vidpathname = new Vidpathname("");

    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS video"
            + "(name TEXT NOT NULL PRIMARY KEY,"
            + " size INTEGER,"
            + " date INTEGER,"
            + " duration REAL)";

    private static final String URL
            = "jdbc:sqlite:Sample.db";

    public static void main(String[] args)  {
//        Vidpathname vidpathname = new Vidpathname(args);
//        System.out.println("ファイルサイズは" + vidpathname.getVidpathname() + "です。");
//         引数チェック
        String vidPath = new checkArgs(args).getterFilePath();
//         引数ファイルパス存在確認
        video vidName = new video(vidPath);
        System.out.println("両端置換後の名前は" + vidName.getterReplacedName() + "です。");
        System.out.println("ファイルサイズは" + vidName.getterSize() + "です。");
        System.out.println("放送日は" + vidName.getterDate() + "です。");
        System.out.println("長さは" + vidName.getterDuration() + "です。");
        videoDao vidDao = new videoDao(SQL_CREATE, URL);
        System.out.println("vidDao.SQL..." + videoDao.SQL_SELECT);
//         テーブル存在チェック、名前,放送日,サイズ情報を取得
        System.out.println(vidName.getterReplacedName());
        System.out.println(vidName.getterSize());
        System.out.println(vidName.getterDate());
        Map<String, Object> _dbData = vidDao.isunique(vidName.getterReplacedName(), vidName.getterSize(), vidName.getterDate());
        System.out.println("DBDAT:" + _dbData);
//        カラム存在確認
        if ((boolean) _dbData.get("isnull")) {
            System.out.println("カラム有");
            System.out.println("isnull___:" + _dbData.get("isnull"));
            if (vidPath.contains(".ts")) {
                new delVideo(vidPath);
            } else {
//                一意チェック
                boolean bRet = new isUnique(_dbData.get("name"), vidName.getterReplacedForwardMatchedName(), _dbData.get("size"), vidName.getterSize(), _dbData.get("date"), vidName.getterDate()).getterIsUnique();
                if (bRet) {
                    System.out.println("引数のPramは一意です。");
                } else {
                    System.out.println("引数のPramは一意ではありません。");
                    System.out.println("sizeChk____________");
                    System.out.println("DBsize_:" + _dbData.get("size"));
                    System.out.println("DROPsize_:" + vidName.getterSize());
                    if (_dbData.get("size") != null) {
                        long sizeChk = (long) _dbData.get("size") - vidName.getterSize();
                        System.out.println("sizeChk____________" + sizeChk);
//                         DB<Dropが500MB以下の場合放送日チェック、500MB以上の場合はエラー
                        if (sizeChk < 500000000) {
                            System.out.println("ドロップファイルは500B以下");
//                             DB<Drop Dropの放送日の方が新しいなら削除
                            if ((long) _dbData.get("date") < vidName.getterDate()) {
                                System.out.println("削除します。");
                                new delVideo(vidPath);
                            }
                        } else {
                            System.out.println("ドロップファイルは500B以上。\rエラー");
                            System.out.println("削除しません。");
                        }
                    }
                }
            }
        } else {
            System.out.println("カラム無");
            System.out.println("is_null___:" + _dbData.get("isnull"));
//             拡張子がMP4の場合insert
            if (vidPath.contains(".mp4")) {
                System.out.println("MP4 insert...");
                vidDao.insertStoredProc(vidName.getterReplacedForwardMatchedName(), vidName.getterSize(), vidName.getterDate(), vidName.getterDuration());
            }
        }
    }
}
