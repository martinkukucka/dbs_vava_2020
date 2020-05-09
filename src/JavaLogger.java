import java.util.logging.*;
import java.io.IOException;

public class JavaLogger {

    public static Logger logger = Logger.getLogger("Logger");
    public static FileHandler fh = null;

    public JavaLogger() {
        init();
    }

    public static void init() {

        try {
            fh = new FileHandler("app.log", false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }
}



