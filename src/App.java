import controller.Controller;
import controller.Logging;
import model.Model;
import model.transports.Transport;
import view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

// Основной класс программы
public class App {
    public static Logging errCore = new Logging();
    public static Model model = new Model();
    public static View view = new View();
    public static Controller controller = new Controller(model, view, errCore);
    public static Properties prop;

    public static void main(String[] args) {
        boolean isRunning = false; // true - app is running, false - app is stopped

        // Точка запуска программы
        App app = new App();
        app.run(model, view, controller, prop, isRunning);
    }

    // Метод при запуске программы
    public void run(Model model, View view, Controller controller, Properties prop, boolean isRunning) {
        isRunning = true;

        while (isRunning) {
            if (model.getGroup().equals(Model.Groups.developer.toString())) {
                // Функция выбранная пользователем
                 int function = View.menu(View.Menus.developer);

                 switch (function) {
                     case 1:
                         View.send_info(String.valueOf(Controller.errCore.getErrCount()));
                         view.print_info(model);
                         break;
                     case 2:
                         controller.addTransport(Transport.Types.Car);
                         break;
                     case 3:
                         Model.clearTransports();
                         break;
                     case 4:
                         if (Model.debug == true) {
                             Model.debug = false;
                             Model.configProp.replace(Model.getLogin()+"__debug", String.valueOf(Model.debug));
                         }
                         else {
                             Model.debug = true;
                             Model.configProp.replace(Model.getLogin()+"__debug", String.valueOf(Model.debug));
                         }
                         break;
                     case 5:
                         System.out.println(Model.autotest);
                         if (Model.autotest == true) {
                             Model.autotest = false;
                             Model.configProp.replace(Model.getLogin()+"__autotest", String.valueOf(Model.autotest));
                         }
                         else {
                             Model.autotest = true;
                             Model.configProp.replace(Model.getLogin()+"__autotest", String.valueOf(Model.autotest));
                         }
                         System.out.println(Model.autotest);
                         break;
                     case 6:
                         // Работа с файлом
                         PrintWriter writer = null;
                         try {
                             writer = new PrintWriter(model.getConfigFileName());
                         } catch (FileNotFoundException e) {
                             throw new RuntimeException(e);
                         }
                         writer.print("");
                         writer.close();

                         boolean isLogin = false;
                         function = View.menu(View.Menus.auth);
                         while (!isLogin)
                         {
                             if (function == 1)
                             {
                                 if (!model.login()) {
                                     if (Controller.inputInt("1) Продолжить попытки ввода(по умолчанию)\n2) Регистрация\n>>> ") == 2) {
                                         model.registration();
                                         isLogin = true;
                                     }
                                 }
                                 else {
                                     isLogin = true;
                                 }
                             }
                             else if (function == 2) {
                                 model.registration();
                                 isLogin = true;
                             }
                         }
                         break;
                     case 7:
                         // Сохраняем и выходим
                         model.fromObjectToXML();
                         model.saveConfigFile(model.usersFile, model.usersProp);
                         model.saveConfigFile(model.configFile, model.configProp);
                         System.exit(0);
                         break;
                 }
            }
            else if (model.getGroup().equals(Model.Groups.user.toString())) {
                int function = View.menu(View.Menus.user);

                switch (function) {
                    case 1:
                        view.print_info(model);
                        break;
                    case 2:
                        controller.addTransport(Transport.Types.Car);
                        break;
                    case 3:
                        model.setTransport(new Transport());
                        Logging.log(this, "Transport was cleared!");
                        break;
                    case 4:
                        // Работа с файлом
                        PrintWriter writer = null;
                        try {
                            writer = new PrintWriter(model.getConfigFileName());
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        writer.print("");
                        writer.close();

                        boolean isLogin = false;
                        function = View.menu(View.Menus.auth);
                        while (!isLogin)
                        {
                            if (function == 1)
                            {
                                if (!model.login()) {
                                    if (Controller.inputInt("1) Продолжить попытки ввода(по умолчанию)\n2) Регистрация\n>>> ") == 2) {
                                        model.registration();
                                        isLogin = true;
                                    }
                                }
                                else {
                                    isLogin = true;
                                }
                            }
                            else if (function == 2) {
                                model.registration();
                                isLogin = true;
                            }
                        }
                        break;
                    case 5:
                        // Сохраняем и выходим
                        model.fromObjectToXML();
                        model.saveConfigFile(model.usersFile, model.usersProp);
                        model.saveConfigFile(model.configFile, model.configProp);
                        System.exit(0);
                        break;
                }
            }

        }
    }
}
