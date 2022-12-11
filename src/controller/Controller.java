package controller;

import model.Model;
import model.transports.Transport;
import view.View;

import java.util.Random;
import java.util.Scanner;

public class Controller {
    // <Fields>
    Model model;
    View view;
    public static Logging errCore = new Logging();
    public static Random random = new Random();
    // </Fields>


    // <Constructor>
    public Controller(Model model, View view, Logging errCore) {
        this.model = model;
        this.view = view;
        this.errCore = errCore;
    }
    // </Constructor>


    // <Input methods>
    public static void clear() {
        try
        {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (Exception e) {
            errCore.addErrWithLog(e); //Добавляем ошибку в список и пишем в лог без отображения в консоли
            errCore.showErrText(e); //Отображаем ошибку
        }
    }
    public static String inputString(String msg) {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            if (sc.hasNext())
            {
                clear();
                return sc.next();
            }
        }
    }
    public static int inputInt(String msg) {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            if (sc.hasNextInt())
            {
                clear();
                return sc.nextInt();
            }
            else {
                View.send_error(Controller.errCore.makeErr(new Exception("Wrong value.")));
            }
        }
    }
    public static int inputInt(String msg, int start, int end) {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            if (sc.hasNextInt())
            {
                int temp = sc.nextInt();
                if (temp >= start && temp <= end) {
                    return temp;
                }
            }
            else {
                View.send_error(Controller.errCore.makeErr(new Exception("Wrong value.")));
            }
        }
    }
    public static double inputDouble(String msg) {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            if (sc.hasNextDouble())
            {
                clear();
                return sc.nextDouble();
            }
            else {
                View.send_error(Controller.errCore.makeErr(new Exception("Wrong value.")));
            }
        }
    }
    public static boolean inputBoolean(String msg) {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            if (sc.hasNextBoolean())
            {
                clear();
                return sc.nextBoolean();
            }
            else {
                View.send_error(Controller.errCore.makeErr(new Exception("Wrong value.")));
            }
        }
    }
    // </Input methods>


    // <Other methods>
    public void addTransport(Transport.Types type) {
        this.model.getTransport().add(model);

        Logging.log(this, "Add transport");
    }

    public static String generateString(int length)
    {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
    // </Other methods>
}