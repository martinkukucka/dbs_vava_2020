import java.util.logging.*;
import java.io.IOException;

// !
public class JavaLogger {

    public static Logger logger = Logger.getLogger("Logger");
    private static FileHandler fh = null;

    public JavaLogger() {
        init();
    }

    static void init() {

        try {
            fh = new FileHandler("app.log", false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }
}



