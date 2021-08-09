import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

class DownloadingHeartBeat extends Thread {
    private volatile boolean beating = true;

    @Override
    public void run() {
        String[] dots = {
                ".",
                "..",
                "...",
                "...."
        };

        while (beating) {
            for (String dot : dots) {
                System.out.println(dot);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void shutDown() {this.beating = false;}
}

class FileDownloader extends Thread {
    private String url;
    private String fileName;

    FileDownloader(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            System.out.println("Started to download: " + fileName);

            URL resourceToDownload = new URL(url);
            URLConnection connection = resourceToDownload.openConnection();
            InputStream input = connection.getInputStream();

            File fileToSave = new File(fileName);
            Files.copy(input, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
            input.close();
        } catch (IOException e) {
            System.err.println("Failed to download the file: " + e.getMessage());
        }
    }
}

public class ThreadJoinExample {
    public static void main(String[] args) {
        FileDownloader downloader = new FileDownloader("https://goo.gl/nqZJn4", "jugbd-meetup7_1.jpeg");
        FileDownloader downloader1 = new FileDownloader("https://goo.gl/UoSMMt", "jugbd-meetup7_2.jpeg");

        DownloadingHeartBeat heartBeat = new DownloadingHeartBeat();

        downloader.start();
        downloader1.start();
        heartBeat.start();

        try {
            downloader.join();
            downloader1.join();

            heartBeat.shutDown();

            heartBeat.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nThe download is completed");
    }
}