package controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.*;

public class Logging {
    private static ArrayList<Exception> ErrList = new ArrayList<Exception>();
    private static Logger logger;

    public Logging() {
        ErrList = new ArrayList();
        //Читаем конфигурационный файл лога
        try {
            LogManager.getLogManager().readConfiguration(Logging.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger = Logger.getLogger(Logging.class.getName());
    }

    public static void log(Object caller, String msg) {
        logger = Logger.getLogger(caller.getClass().getName());
        logger.setUseParentHandlers(false);
        FileHandler fh = null;

        try {
            fh = new FileHandler("app.log", true);
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

                @Override
                public synchronized String format(LogRecord lr) {
                    return String.format(caller.getClass().getName().toUpperCase() + " | " + format,
                            new Date(lr.getMillis()),
                            lr.getLevel().getLocalizedName(),
                            lr.getMessage()
                    );
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO, "\n\t>>"+msg);
        fh.close();
    }

    public static void log(String file, Object caller, String msg) {
        logger = Logger.getLogger(caller.getClass().getName());
        logger.setUseParentHandlers(false);
        FileHandler fh = null;

        try {
            fh = new FileHandler(file, true);
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

                @Override
                public synchronized String format(LogRecord lr) {
                    return String.format(caller.getClass().getName().toUpperCase() + " | " + format,
                            new Date(lr.getMillis()),
                            lr.getLevel().getLocalizedName(),
                            lr.getMessage()
                    );
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO, "\n\t>>"+msg);
        fh.close();
    }

    //Добавляем ошибку в список ошибок
    public int addErr(Exception e) {
        ErrList.add(e);
        return ErrList.size();
    }

    //Добавляем ошибку в список ошибок и в лог
    public int addErrWithLog(Exception e) {
        ErrList.add(e);
        //Код записи сообщения в лог
        logger.log(Level.FINE, e.getMessage());
        return ErrList.size();
    }

    //Получаем количество ошибок
    public int getErrCount() {
        return ErrList.size();
    }

    //Выводим информацию об ошибке
    public void showErrText(Exception e) {
        System.err.println(e.getMessage());
    }

    //Генерим (пробрасываем ошибку) с фиксацией в списке ошибок
    public String makeErr(Exception e) {
        addErr(e);
        addErrWithLog(e);
        return new Exception(e).getMessage();
    }
}