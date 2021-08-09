import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class TextCopier implements Callable<String> {
    private String target;

    TextCopier(String url) {this.target = url;}

    @Override
    public String call() throws Exception {
        URL url = new URL(target);
        StringBuilder builder = new StringBuilder();

        try(InputStream inputStream = url.openConnection().getInputStream()) {
            Scanner scanner = new Scanner(inputStream);

            while (scanner.hasNext()) {
                builder.append(scanner.nextLine());
            }
        }

        return builder.toString();
    }
}