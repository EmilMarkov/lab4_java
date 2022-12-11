package model;

import controller.Controller;
import controller.Logging;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.transports.Transport;
import view.View;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Model {
    public enum Groups {
        developer ("developer"),
        user ("user");

        private final String name;

        private Groups(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }

    // <Fields>
    private static Map<String, Transport> transport = new HashMap<String, Transport>();
    final public static String usersFile = "users.ini";
    final public static String configFile = "config.ini";
    final private static String transportsXMLFile = "transports.xml";
    private static String login;
    private static String password;
    private static String group;
    public static boolean debug;
    public static boolean autotest;
    private Map<String, String> database = new HashMap<String, String>();
    public static Properties usersProp = new Properties();
    public static Properties configProp = new Properties();
    private JUnitTest testCore = new JUnitTest();
    // </Fields>


    // <Constructors>
    public Model() {
        try {
            FileWriter fw = new FileWriter("app.log", true);

            fw.append("\n\t\t[NEW SESSION]\n\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Filling in Properties
        try {
            getConfigFile(usersFile, usersProp);
            getConfigFile(configFile, configProp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Filling in users
        loadUsers();
        loadCurrentUser();

        // Auth
        boolean isLogin = false;

        if (!loadUserConfig()) {
            int function = View.menu(View.Menus.auth);
            while (!isLogin) {
                if (function == 1) {
                    if (!login()) {
                        if (Controller.inputInt("1) Продолжить попытки ввода(по умолчанию)\n2) Регистрация\n>>> ") == 2) {
                            registration();
                            isLogin = true;
                        }
                    }
                    else {
                        isLogin = true;
                    }
                }
                else {
                    registration();
                    isLogin = true;
                }
            }
        }

        // Filling in Transport class
        transport.put(login, fromXmlToObject());

        if (autotest == true) {
            try {
                Transport testTransport = new Transport();

                long startTime = System.currentTimeMillis();
                testCore.testAddArrayList(10, "app.log.1", testTransport);
                long endTime = System.currentTimeMillis();
                View.send_test("1 | 10 добавлений     | " + (endTime - startTime) + " milliseconds");
                testTransport.removeDefaultCar(10);

                startTime = System.currentTimeMillis();
                testCore.testAddArrayList(100, "app.log.2", testTransport);
                endTime = System.currentTimeMillis();
                View.send_test("2 | 100 добавлений    | " + (endTime - startTime) + " milliseconds");
                testTransport.removeDefaultCar(100);

                startTime = System.currentTimeMillis();
                testCore.testAddArrayList(1000, "app.log.3", testTransport);
                endTime = System.currentTimeMillis();
                View.send_test("3 | 1000 добавлений   | " + (endTime - startTime) + " milliseconds");
                testTransport.removeDefaultCar(1000);

                startTime = System.currentTimeMillis();
                testCore.testAddArrayList(10000, "app.log.4", testTransport);
                endTime = System.currentTimeMillis();
                View.send_test("4 | 10000 добавлений  | " + (endTime - startTime) + " milliseconds");
                testTransport.removeDefaultCar(10000);

                startTime = System.currentTimeMillis();
                testCore.testAddArrayList(100000, "app.log.5", testTransport);
                endTime = System.currentTimeMillis();
                View.send_test("5 | 100000 добавлений | " + (endTime - startTime) + " milliseconds");
                testTransport.removeDefaultCar(100000);

                View.send_info("Тесты для ArrayList успешно пройдены!");
                Logging.log(this, "Tests for ArrayList were successfully passed!");

                startTime = System.currentTimeMillis();
                testCore.testAddHashMap(10, "app.log.6", testTransport);
                endTime = System.currentTimeMillis();
                View.send_test("1 | 10 добавлений     | " + (endTime - startTime) + " milliseconds");
                testTransport.removeDefaultCarMap(10);

                startTime = System.currentTimeMillis();
                testCore.testAddHashMap(100, "app.log.7", testTransport);
                endTime = System.currentTimeMillis();
                View.send_test("2 | 100 добавлений    | " + (endTime - startTime) + " milliseconds");
                testTransport.removeDefaultCarMap(100);

                startTime = System.currentTimeMillis();
                testCore.testAddHashMap(1000, "app.log.8", testTransport);
                endTime = System.currentTimeMillis();
                View.send_test("3 | 1000 добавлений   | " + (endTime - startTime) + " milliseconds");
                testTransport.removeDefaultCarMap(1000);

                startTime = System.currentTimeMillis();
                testCore.testAddHashMap(10000, "app.log.9", testTransport);
                endTime = System.currentTimeMillis();
                View.send_test("4 | 10000 добавлений  | " + (endTime - startTime) + " milliseconds");
                testTransport.removeDefaultCarMap(10000);

                startTime = System.currentTimeMillis();
                testCore.testAddHashMap(100000, "app.log.10", testTransport);
                endTime = System.currentTimeMillis();
                View.send_test("5 | 100000 добавлений | " + (endTime - startTime) + " milliseconds");
                testTransport.removeDefaultCarMap(100000);

                View.send_info("Тесты для HashMap успешно пройдены!");
                Logging.log(this, "Tests for ArrayList were successfully passed!");
            }
            catch(org.junit.ComparisonFailure ex) {
                View.send_warning("Error in tests!\n\t"+ex.getMessage());
                Logging.log(this, "Error in tests!\n\t"+ex.getMessage());
            }
        }
        if (debug == true) {

        }

        System.out.println(autotest);
        System.out.println(debug);

        if (debug) {
            Logging.log(this, "Model inited");
        }
    }
    // </Constructors>


    // <Getters>
    public Transport getTransport() { return transport.get(login); }
    public void setTransport(Transport transport) { this.transport.put(login, transport); }
    public static String getLogin() { return login; }
    public static String getPassword() { return password; }
    public Properties getProp() { return usersProp; }
    public static String getGroup() { return group; }
    public String getConfigFileName() { return configFile; }
    // </Getters>


    // <Authentication>
    public boolean login() {
        boolean isLogin = false;
        try {
            String loginTmp = "";
            String passwordTmp = "";

            while (true) {
                loginTmp = Controller.inputString("Auth:\n\tLogin: ");
                passwordTmp = Controller.inputString("\tPassword: ");

                for (String loginValue: database.keySet())
                {
                    if (loginTmp.equals(loginValue))
                    {
                        if (passwordTmp.equals(database.get(loginValue)))
                        {
                            login = loginTmp;
                            password = passwordTmp;
                            group = database.get(login+"__group");
                            isLogin = true;
                            if (database.get(loginTmp+"__autotest").equals("true")) {
                                autotest = true;
                            }
                            else {
                                autotest = false;
                            }
                            if (database.get(loginTmp+"__debug").equals("true")) {
                                debug = true;
                            }
                            else {
                                debug = false;
                            }
                            configProp.put(login, password);
                            configProp.put(login+"__group", group);
                            configProp.put(login+"__autotest", String.valueOf(autotest));
                            configProp.put(login+"__debug", String.valueOf(debug));
                            saveConfigFile(configFile, configProp);
                        }
                    }
                }

                if (!isLogin)
                {
                    boolean answer = Controller.inputBoolean("User not found!\nDo you want to reg? true|false: ");
                    if (answer) {
                        registration();
                        isLogin = true;
                    }
                }
                else {
                    break;
                }
            }

            Logging.log(this, "Login");
        }
        catch(Exception e) {
            View.send_error(Controller.errCore.makeErr(new Exception("Ошибка авторизации. " + e.getMessage())));
        }
        return isLogin;
    }
    public void registration() {
        boolean loginFlag = false;
        boolean passwordFlag = false;

        while(!loginFlag && !passwordFlag)
        {
            String login_field = Controller.inputString("Enter login: ");
            String password_field = Controller.inputString("Enter password: ");
            String group_field = Controller.inputString("Enter group: ");

            if (!Groups.developer.equalsName(group_field) && !Groups.user.equalsName(group_field)) {
                System.out.println("Wrong group value!");
                continue;
            }

            if (!database.keySet().contains(login_field))
            {
                usersProp.put(login_field, password_field);
                usersProp.put(login_field+"__group", group_field);
                usersProp.put(login_field+"__autotest", false);
                usersProp.put(login_field+"__debug", false);
                configProp.put(login_field, password_field);
                configProp.put(login_field+"__group", group_field);
                configProp.put(login_field+"__autotest", false);
                configProp.put(login_field+"__debug", false);
                login = login_field;
                password = password_field;
                group = group_field;
                autotest = false;
                debug = false;
                loginFlag = true;
                passwordFlag = true;
                saveConfigFile(usersFile, usersProp);
                saveConfigFile(configFile, configProp);
                loadUsers();
            }
            else {
                System.out.println("User already exist! Try again...");
            }
        }

        Logging.log(this, "Registration");
    }
    public void loadUsers() {
        PrintWriter writer = new PrintWriter(System.out);

        try {
            for (final String name: usersProp.stringPropertyNames())
                database.put(name, usersProp.getProperty(name));

            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Logging.log(this, "Load users:\n\t\tProp: " + database.toString());
    }
    public void loadCurrentUser() {
        PrintWriter writer = new PrintWriter(System.out);

        try {
            for (final String name: configProp.stringPropertyNames()) {
                if (!name.contains("__group")) {
                    login = name;
                    password = configProp.getProperty(login);
                    group = configProp.getProperty(login+"__group");
                }
            }

            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Logging.log(this, "Load users:\n\t\tProp: " + database.toString());
    }
    public void saveUsers() {
        for (String loginValue: database.keySet())
        {
            usersProp.put(loginValue, database.get(loginValue));
        }
        saveConfigFile(usersFile, usersProp);

        Logging.log(this, "Save users");
    }
    public boolean loadUserConfig() {
        PrintWriter writer = new PrintWriter(System.out);

        boolean isLoad = true;

        try {
            for (final String name: configProp.stringPropertyNames()) {
                if (!name.contains("__group") && !name.contains("__autotest") && !name.contains("__debug"))
                {
                    login = name;
                    password = configProp.getProperty(login);
                    group = configProp.getProperty(name+"__group");
                    if (configProp.getProperty(name+"__autotest").equals("true")) {
                        autotest = true;
                    }
                    else {
                        autotest = debug;
                    }
                    if (configProp.getProperty(name+"__debug").equals("true")) {
                        debug = true;
                    }
                    else {
                        debug = debug;
                    }
                }
            }

            writer.flush();

            if (configProp.isEmpty()) {
                isLoad = false;
            }

            if (debug) {
                Logging.log(this, "User was loaded!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Logging.log(this, "Load user config:\n\t\tProp: " + database.toString());

        return isLoad;
    }
    // </Authentication>


    // <Data>
    public Transport fromXmlToObject() {
        try {
            // creating a JAXBContext object - an entry point for JAXB
            JAXBContext context = JAXBContext.newInstance(Transport.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            if (new File(transportsXMLFile).length() <= 60) {
                return new Transport();
            }
            else {
                return (Transport)unmarshaller.unmarshal(new File(transportsXMLFile));
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        Logging.log(this, "From xml to object");

        return null;
    }
    public void fromObjectToXML()  {
        try {
            JAXBContext context = JAXBContext.newInstance(Transport.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(this.transport.get(login), new File(transportsXMLFile));

        } catch (JAXBException e) {
            View.send_error(Controller.errCore.makeErr(new Exception("Конфигурационный файл не читается. Ошибка - " + e.getMessage())));
        }

        Logging.log(this, "From object to xml");
    }
    public void getConfigFile(String fileName, Properties prop) throws Exception {
        InputStream fs;
        try {
            File f = new File("././resources/"+ fileName);

            if (f.exists()) {
                fs = new FileInputStream(f);
            } else {
                fs = Model.class.getResourceAsStream(fileName);
            }

            prop.load(fs);
            View.send_info("Конфигурация загружена");
            assert fs != null;
            fs.close();
        } catch (FileNotFoundException e) {
            View.send_error(Controller.errCore.makeErr(new Exception("Конфигурационный файл не найден. Ошибка - " + e.getMessage())));
        } catch (IOException e) {
            View.send_error(Controller.errCore.makeErr(new Exception("Конфигурационный файл не читается. Ошибка - " + e.getMessage())));
        }

        Logging.log(this, "Get config file:\n\t\tFile: " + fileName + "\n\t\tProp: " + prop.toString());
    }
    public void saveConfigFile(String fileName, Properties prop) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("././resources/"+fileName);
            prop.store(out, null);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Logging.log(this, "Save config file");
    }
    // </Data>


    // <Other methods>
    public void runDebug() {
        debug = true;

        Logging.log(this, "Debug");
    }
    public void runAutotest() {
        autotest = true;

        Logging.log(this, "Autotests");
    }
    public static void clearTransports() {
        transport.replace(login, new Transport());
        if (debug) {
            Logging.log(Model.class, "Transports was cleared!");
        }
    }
    public static Object getFieldValue(Object object, String fieldName) {
        Class clazz = object.getClass();
        try {
            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch(Exception ex) {
            return null;  //нет такого поля
        }
    }
    // </Other methods>
}