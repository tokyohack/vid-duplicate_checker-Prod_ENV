# Duplicate video checker
これはなに?

名前、サイズ、放送日をDBに追加する重複ファイルと思われるファイルは削除します。  
ファイル名から放送日、置換後のタイトルを取得。  
ファイルサイズに一定以上差が無い時にのみ削除。  

## 必須
FFmpeg     FFmpegをラップしている為  
ファイル名が ^Record_20XX1231-235959 - となっている事  
20XX1231-235959をUnixTimeに変換し放送日をコンペアする為

## 実行方法
```
.\java.exe -classpath .\target\classes video.Videomain "絶対パス"
```

## イメージ
![image](https://gitimagefolder.s3.ap-northeast-1.amazonaws.com/vid-duplicate_checker-Prod_ENV/image.png)