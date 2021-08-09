public class Main {
    public static void main(String[] args) {
        final String downloadDir = "./";
        final String url = "http://radiospecials.fusionbd.com/download.php?file=All_Files/Bhoot_FM/High_Quality-MP3_Version/October_2019/Bhoot_FM-25-10-2019_FusionBD.Com.mp3";

        DownloadManager downloadManager = new DownloadManager();

        downloadManager.download(url, downloadDir);
    }
}
