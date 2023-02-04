// 引数から受け取ったファイルから名前,放送日,サイズを返す
package video;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class video {
    private String forwardMatched;
    private String replacedName;
    private long date;
    private double duration;
    private long size;
    private final String videoPath;

    public video(String argVideoPath) {
        this.videoPath = argVideoPath;
    }

    public String getterReplacedName() {
        forwardMatched = this.videoPath.replaceAll("^.*\s-", "");
        replacedName = forwardMatched.replaceAll(("\\[新\\]|\\[無\\]|\\[初\\]|選|\\[再\\]|　|▽|「|」|"), "\\%");
        return replacedName = replacedName.replaceAll("～", "\\%～");
    }

    public String getterReplacedForwardMatchedName() {
        return forwardMatched = this.videoPath.replaceAll("^.*\s-", "");
    }

    public long getterSize() throws IOException {
        return size = Files.size(Path.of(this.videoPath));
    }

    public long getterDate() {
        String record = this.videoPath.replaceAll("^.*(?=\\d{8}-\\d{6})", "");
        record = record.replaceAll("(-|\s-.*)", "");
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("uuuuMMddHHmmss");
        LocalDateTime ldt = LocalDateTime.parse(record, timeformat);
        OffsetDateTime odt = ldt.atOffset(ZoneOffset.ofHours(9));
        date = odt.toEpochSecond();
        return date;
    }

    public double getterDuration() throws IOException {
        FFprobe ffprobe = new FFprobe("ffprobePath");
        FFmpegProbeResult probeResult = null;
        Path of = Path.of(videoPath);
        try {
            probeResult = ffprobe.probe(videoPath);
        } catch (IOException e0) {
            String ERR = String.valueOf(e0);
            if (ERR.contains("Timed out waiting")) {
                System.out.println("\nERRR_Timed out waiting");
            }
            System.err.println("\n" + e0 + "\nERRR");

            System.exit(0);
        }
        probeResult.hasError();
        FFmpegFormat format = probeResult.getFormat();
        try {
            System.out.format(
                    "%nFile: '%s' ; Format: '%s' ; Duration: %.3fs"
                    , format.filename
                    , format.format_long_name
                    , format.duration
            );
        } catch (NullPointerException e0) {
            if (String.valueOf(e0).contains("Timed out waiting")) {
                System.err.println("\n" + e0 + "\nERRR");
                System.exit(0);
            } else {
                System.err.println("\n" + e0 + "\nERRR");
                System.err.println("読み込みエラーです\n" + e0 + "\nERRR");
                new delVideo(videoPath);
                System.exit(0);
            }
        }
        System.out.format("%nFile: '%s' ; Format: '%s' ; Duration: %.3fs",
                format.filename,
                format.format_long_name,
                format.duration
        );
        System.out.format("\n");
        FFmpegStream stream = probeResult.getStreams().get(0);
        System.out.format("%nCodec: '%s' ; Width: %dpx ; Height: %fs",
                stream.codec_long_name,
                stream.width,
                stream.duration
        );
        int sec = (int) (format.duration % 60);
        int hour = (int) (format.duration / 60) / 60;

        int min = (int) ((format.duration / 60) % 60);
        System.out.println();
        System.out.println("hour:" + hour + ",min:" + min + ",SEC:" + sec);
        duration = format.duration;
        return duration;
    }

}