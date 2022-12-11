package model.transports;

import controller.Controller;
import jakarta.xml.bind.annotation.*;
import controller.Logging;
import model.Model;
import view.View;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@XmlRootElement(name= "Transport")
@XmlType(propOrder = {"brand", "model", "maxLoad", "maxPassengerCount", "maxSpeed", "cars","buses", "motocycles", "trucks"})
public class Transport {
    // <Enum>
    public enum Types {
        All,
        Car,
        Bus,
        Motocycle,
        Truck
    }

    // <Fields>
    protected String brand;
    protected String model;
    protected double maxLoad;
    protected double maxPassengerCount;
    protected double maxSpeed;
    protected List<Car> cars = new ArrayList<Car>();
    private Map<String, Car> carsMap = new HashMap<String, Car>();
    protected List<Bus> buses = new ArrayList<Bus>();
    protected List<Motocycle> motocycles = new ArrayList<Motocycle>();
    protected List<Truck> trucks = new ArrayList<Truck>();
    // </Fields>


    // <Constructor>
    public Transport() {
    }
    // </Constructor>


    // <Getters>

    @XmlElement
    public String getBrand() {
        return brand;
    }

    @XmlElement
    public String getModel() {
        return model;
    }

    @XmlElement
    public double getMaxLoad() {
        return maxLoad;
    }

    @XmlElement
    public double getMaxPassengerCount() {
        return maxPassengerCount;
    }

    @XmlElement
    public double getMaxSpeed() { return maxSpeed; }
    public Map<String, Car> getCarsMap() { return carsMap; }

    @XmlElementWrapper(name="carsList")
    @XmlElement(name="carItem")
    public List<Car> getCars() { return cars; }

    @XmlElementWrapper(name="busesList")
    @XmlElement(name="busItem")
    public List<Bus> getBuses() {
        return buses;
    }

    @XmlElementWrapper(name="motocyclesList")
    @XmlElement(name="motocycleItem")
    public List<Motocycle> getMotocycles() { return motocycles; }

    @XmlElementWrapper(name="trucksList")
    @XmlElement(name="truckItem")
    public List<Truck> getTrucks() {
        return trucks;
    }
    // </Getters>


    // <Setters>
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setMaxLoad(double maxLoad) {
        this.maxLoad = maxLoad;
    }
    public void setMaxPassengerCount(double maxPassengerCount) {
        this.maxPassengerCount = maxPassengerCount;
    }
    public void setMaxSpeed(double maxSpeed) { this.maxSpeed = maxSpeed; }
    // </Setters>


    // <Other methods>
    public void add(Model model) {
        boolean isSelect = false;
        Types type = Types.Car;

        while (!isSelect) {
            int user_choice = Controller.inputInt("1) Car\n2) Bus\n3) Motocycle\n4) Truck\n", 1, 4);
            switch (user_choice) {
                case 1:
                    type = Types.Car;
                    isSelect = true;
                    break;
                case 2:
                    type = Types.Bus;
                    isSelect = true;
                    break;
                case 3:
                    type = Types.Motocycle;
                    isSelect = true;
                    break;
                case 4:
                    type = Types.Truck;
                    isSelect = true;
                    break;
            }
        }

        switch (type) {
            case Car:
                Car carTemp = new Car();

                carTemp.setId(Model.getLogin());
                carTemp.setBrand(Controller.inputString("Brand: "));
                carTemp.setModel(Controller.inputString("Model: "));
                carTemp.setMaxLoad(Controller.inputDouble("Max load: "));
                carTemp.setMaxPassengerCount(Controller.inputInt("Max passenger count: "));
                carTemp.setMaxSpeed(Controller.inputDouble("Max speed: "));
                carTemp.setBodyType(Controller.inputString("Body type: "));
                carTemp.setEquipment(Controller.inputString("Equipment: "));
                carTemp.setIsTrailer(Controller.inputBoolean("Is trailer? Yes - true, No - false: "));

                if (carTemp.getIsTrailer()) {
                    carTemp.getTrailer().setType(Controller.inputString("Trailer type: "));
                    carTemp.getTrailer().setMaxLoad(Controller.inputInt("Trailer max load: "));
                }

                model.getTransport().cars.add(carTemp);

                Logging.log(this, "Add, ID = " + Model.getLogin());

                break;
            case Bus:
                Bus busTemp = new Bus();

                busTemp.setId(Model.getLogin());
                busTemp.setBrand(Controller.inputString("\nBrand: "));
                busTemp.setModel(Controller.inputString("Model: "));
                busTemp.setMaxLoad(Controller.inputDouble("Max load: "));
                busTemp.setMaxPassengerCount(Controller.inputInt("Max passenger count: "));
                busTemp.setMaxSpeed(Controller.inputDouble("Max speed: "));
                busTemp.setType(Controller.inputString("Type: "));
                busTemp.setEquipment(Controller.inputString("Equipment: "));

                model.getTransport().buses.add(busTemp);

                Logging.log(this, "Add, ID = " + Model.getLogin());

                break;
            case Motocycle:
                Motocycle motocycleTemp = new Motocycle();

                motocycleTemp.setId(Model.getLogin());
                motocycleTemp.setBrand(Controller.inputString("\nBrand: "));
                motocycleTemp.setModel(Controller.inputString("Model: "));
                motocycleTemp.setMaxLoad(Controller.inputDouble("Max load: "));
                motocycleTemp.setMaxPassengerCount(Controller.inputInt("Max passenger count: "));
                motocycleTemp.setMaxSpeed(Controller.inputDouble("Max speed: "));
                motocycleTemp.setType(Controller.inputString("Type: "));
                motocycleTemp.setEquipment(Controller.inputString("Equipment: "));

                model.getTransport().motocycles.add(motocycleTemp);

                Logging.log(this, "Add, ID = " + Model.getLogin());

                break;
            case Truck:
                Truck truckTemp = new Truck();

                truckTemp.setId(Model.getLogin());
                truckTemp.setBrand(Controller.inputString("\nBrand: "));
                truckTemp.setModel(Controller.inputString("Model: "));
                truckTemp.setMaxLoad(Controller.inputDouble("Max load: "));
                truckTemp.setMaxPassengerCount(Controller.inputInt("Max passenger count: "));
                truckTemp.setMaxSpeed(Controller.inputDouble("Max speed: "));
                truckTemp.setBodyType(Controller.inputString("Body type: "));
                truckTemp.setEquipment(Controller.inputString("Equipment: "));
                truckTemp.setIsTrailer(Controller.inputBoolean("Is trailer? Yes - true, No - false: "));

                if (truckTemp.getIsTrailer()) {
                    truckTemp.getTrailer().setType(Controller.inputString("Trailer type: "));
                    truckTemp.getTrailer().setMaxLoad(Controller.inputInt("Trailer max load: "));
                }

                model.getTransport().trucks.add(truckTemp);

                Logging.log(this, "Add, ID = " + Model.getLogin());

                break;
            default:
                System.out.println("Unexpected type!");
                System.exit(0);
                break;
        }
    }
    public void printInfo(Types type) {

        switch (type)
        {
            case All:
                printInfo(Types.Car);
                printInfo(Types.Bus);
                printInfo(Types.Motocycle);
                printInfo(Types.Truck);
                break;
            case Car:
                if (cars.size() != 0) View.send_text("\n[Car]");
                for (Car car : cars)
                {
                    if (car.getId() != null) {
                        if (car.getId().equals(Model.getLogin())) {
                            System.out.println("\tInformation about " + car.getBrand() + " " + car.getModel() + ":");
                            System.out.println("\tBrand: " + car.getBrand());
                            System.out.println("\tModel: " + car.getModel());
                            System.out.println("\tMax load: " + String.valueOf(car.getMaxLoad()));
                            System.out.println("\tMax passenger count: " + String.valueOf(car.getMaxPassengerCount()));
                            System.out.println("\tMax speed: " + String.valueOf(car.getMaxSpeed()));
                            System.out.println("\tBody type: " + String.valueOf(car.getBodyType()));
                            System.out.println("\tEquipment: " + String.valueOf(car.getEquipment()));
                            if (car.getIsTrailer()) {
                                System.out.println("\tIs trailer: " + String.valueOf(car.getIsTrailer()));
                                System.out.println("\tTrailer type: " + car.getTrailer().getType());
                                System.out.println("\tTrailer max load: " + String.valueOf(car.getTrailer().getMaxLoad()));
                            }
                            else {
                                System.out.println("\tIs trailer: False");
                            }
                            System.out.println();

                            Logging.log(this, "Print, ID = " + Model.getLogin());
                        }
                    }
                }
                break;
            case Bus:
                if (buses.size() != 0) View.send_text("\n[Bus]");
                for (Bus bus : buses)
                {
                    if (bus.getId() != null) {
                        if (bus.getId().equals(Model.getLogin())) {
                            System.out.println("\tInformation about " + bus.getBrand() + " " + bus.getModel() + ":\n");
                            System.out.println("\tBrand: " + bus.getBrand());
                            System.out.println("\tModel: " + bus.getModel());
                            System.out.println("\tMax load: " + String.valueOf(bus.getMaxLoad()));
                            System.out.println("\tMax passenger count: " + String.valueOf(bus.getMaxPassengerCount()));
                            System.out.println("\tMax speed: " + String.valueOf(bus.getMaxSpeed()));
                            System.out.println("\tType: " + String.valueOf(bus.getType()));
                            System.out.println("\tEquipment: " + String.valueOf(bus.getEquipment()));
                            System.out.println();

                            Logging.log(this, "Print, ID = " + Model.getLogin());
                        }
                    }
                }
                break;
            case Motocycle:
                if (motocycles.size() != 0) View.send_text("\n[Motocycle]");
                for (Motocycle motocycle : motocycles)
                {
                    if (motocycle.getId() != null) {
                        if (motocycle.getId().equals((Model.getLogin()))) {
                            System.out.println("\tInformation about " + motocycle.getBrand() + " " + motocycle.getModel() + ":\n");
                            System.out.println("\tBrand: " + motocycle.getBrand());
                            System.out.println("\tModel: " + motocycle.getModel());
                            System.out.println("\tMax load: " + String.valueOf(motocycle.getMaxLoad()));
                            System.out.println("\tMax passenger count: " + String.valueOf(motocycle.getMaxPassengerCount()));
                            System.out.println("\tMax speed: " + String.valueOf(motocycle.getMaxSpeed()));
                            System.out.println("\tType: " + String.valueOf(motocycle.getType()));
                            System.out.println("\tEquipment: " + String.valueOf(motocycle.getEquipment()));
                            System.out.println();

                            Logging.log(this, "Print, ID = " + Model.getLogin());
                        }
                    }
                }
                break;
            case Truck:
                if (trucks.size() != 0) View.send_text("\n[Truck]");
                for (Truck truck : trucks)
                {
                    if (truck.getId() != null) {
                        if (truck.getId().equals(Model.getLogin())) {
                            System.out.println("\tInformation about " + truck.getBrand() + " " + truck.getModel() + ":\n");
                            System.out.println("\tBrand: " + truck.getBrand());
                            System.out.println("\tModel: " + truck.getModel());
                            System.out.println("\tMax load: " + String.valueOf(truck.getMaxLoad()));
                            System.out.println("\tMax passenger count: " + String.valueOf(truck.getMaxPassengerCount()));
                            System.out.println("\tMax speed: " + String.valueOf(this.getMaxSpeed()));
                            System.out.println("\tBody type: " + String.valueOf(truck.getBodyType()));
                            System.out.println("\tEquipment: " + String.valueOf(truck.getEquipment()));
                            if (truck.getIsTrailer()) {
                                System.out.println("\tIs trailer: " + String.valueOf(truck.getIsTrailer()));
                                System.out.println("\tTrailer type: " + truck.getTrailer().getType());
                                System.out.println("\tTrailer max load: " + String.valueOf(truck.getTrailer().getMaxLoad()));
                            }
                            else {
                                System.out.println("\tIs trailer: False");
                            }
                            System.out.println();

                            Logging.log(this, "Print, ID = " + Model.getLogin());
                        }
                    }
                }
                break;
            default:
                System.out.println("Unexpected type!");
                System.exit(0);
                break;
        }
    }
    public void addDefaultCar(int count, String file) {
        for (int i = 0; i < count; i++) {
            Car car = new Car();
            long startTime = System.nanoTime();
            car.setId(Model.getLogin());
            car.setBodyType(Controller.generateString(5));
            car.setEquipment(Controller.generateString(5));
            car.setIsTrailer(false);
            car.brand = Controller.generateString(10);
            car.model = Controller.generateString(10);
            car.maxLoad = Controller.random.nextDouble(1, 100);
            car.maxPassengerCount = Controller.random.nextInt(1, 5);
            car.maxSpeed = Controller.random.nextDouble(140, 300);
            cars.add(car);
            long endTime = System.nanoTime();
            if (count == 10) {
                try(FileWriter writer = new FileWriter("src/results/add_10_id_arraylist.txt", true))
                {
                    writer.write(String.valueOf(i) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/add_10_value_arraylist.txt", true))
                {
                    writer.write(String.valueOf(endTime - startTime) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 100) {
                try(FileWriter writer = new FileWriter("src/results/add_100_id_arraylist.txt", true))
                {
                    writer.write(String.valueOf(i) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/add_100_value_arraylist.txt", true))
                {
                    writer.write(String.valueOf(endTime - startTime) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 1000) {
                try(FileWriter writer = new FileWriter("src/results/add_1000_id_arraylist.txt", true))
                {
                    writer.write(String.valueOf(i) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/add_1000_value_arraylist.txt", true))
                {
                    writer.write(String.valueOf(endTime - startTime) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 10000) {
                try(FileWriter writer = new FileWriter("src/results/add_10000_id_arraylist.txt", true))
                {
                    writer.write(String.valueOf(i) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/add_10000_value_arraylist.txt", true))
                {
                    writer.write(String.valueOf(endTime - startTime) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 100000) {
                try(FileWriter writer = new FileWriter("src/results/add_100000_id_arraylist.txt", true))
                {
                    writer.write(String.valueOf(i) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/add_100000_value_arraylist.txt", true))
                {
                    writer.write(String.valueOf(endTime - startTime) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
//            Logging.log(file, this, "Add, ID = " + i + " " + (endTime - startTime));
        }
    }

    public void removeDefaultCar(int count) {
        Random random = new Random();

        for (int i = 0; i < Math.round(count * 0.1); i++) {
            long startTime = System.nanoTime();
            int num = random.nextInt(0, count - i - 1);
            cars.remove(num);
            long time = System.nanoTime() - startTime;
            if (count == 10) {
                try(FileWriter writer = new FileWriter("src/results/remove_10_id_arraylist.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/remove_10_value_arraylist.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 100) {
                try(FileWriter writer = new FileWriter("src/results/remove_100_id_arraylist.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/remove_100_value_arraylist.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 1000) {
                try(FileWriter writer = new FileWriter("src/results/remove_1000_id_arraylist.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/remove_1000_value_arraylist.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 10000) {
                try(FileWriter writer = new FileWriter("src/results/remove_10000_id_arraylist.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/remove_10000_value_arraylist.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 100000) {
                try(FileWriter writer = new FileWriter("src/results/remove_100000_id_arraylist.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/remove_100000_value_arraylist.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public void addDefaultCarMap(int count, String file) {
        for (int i = 0; i < count; i++) {
            Car car = new Car();
            long startTime = System.nanoTime();
            car.setId(Model.getLogin());
            car.setBodyType(Controller.generateString(5));
            car.setEquipment(Controller.generateString(5));
            car.setIsTrailer(false);
            car.brand = Controller.generateString(10);
            car.model = Controller.generateString(10);
            car.maxLoad = Controller.random.nextDouble(1, 100);
            car.maxPassengerCount = Controller.random.nextInt(1, 5);
            car.maxSpeed = Controller.random.nextDouble(140, 300);
            carsMap.put(String.valueOf(i), car);
            long endTime = System.nanoTime();
            if (count == 10) {
                try(FileWriter writer = new FileWriter("src/results/add_10_id_hashmap.txt", true))
                {
                    writer.write(String.valueOf(i) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/add_10_value_hashmap.txt", true))
                {
                    writer.write(String.valueOf(endTime - startTime) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 100) {
                try(FileWriter writer = new FileWriter("src/results/add_100_id_hashmap.txt", true))
                {
                    writer.write(String.valueOf(i) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/add_100_value_hashmap.txt", true))
                {
                    writer.write(String.valueOf(endTime - startTime) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 1000) {
                try(FileWriter writer = new FileWriter("src/results/add_1000_id_hashmap.txt", true))
                {
                    writer.write(String.valueOf(i) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/add_1000_value_hashmap.txt", true))
                {
                    writer.write(String.valueOf(endTime - startTime) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 10000) {
                try(FileWriter writer = new FileWriter("src/results/add_10000_id_hashmap.txt", true))
                {
                    writer.write(String.valueOf(i) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/add_10000_value_hashmap.txt", true))
                {
                    writer.write(String.valueOf(endTime - startTime) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 100000) {
                try(FileWriter writer = new FileWriter("src/results/add_100000_id_hashmap.txt", true))
                {
                    writer.write(String.valueOf(i) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/add_100000_value_hashmap.txt", true))
                {
                    writer.write(String.valueOf(endTime - startTime) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
//            Logging.log(file, this, "Add, ID = " + i + " " + (endTime - startTime));
        }
    }

    public void removeDefaultCarMap(int count) {
        Random random = new Random();

        for (int i = 0; i < Math.round(count * 0.1); i++) {
            long startTime = System.nanoTime();
            int num = random.nextInt(0, count - i - 1);
            carsMap.remove(num);
            long time = System.nanoTime() - startTime;
            if (count == 10) {
                try(FileWriter writer = new FileWriter("src/results/remove_10_id_hashmap.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/remove_10_value_hashmap.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 100) {
                try(FileWriter writer = new FileWriter("src/results/remove_100_id_hashmap.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/remove_100_value_hashmap.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 1000) {
                try(FileWriter writer = new FileWriter("src/results/remove_1000_id_hashmap.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/remove_1000_value_hashmap.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 10000) {
                try(FileWriter writer = new FileWriter("src/results/remove_10000_id_hashmap.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/remove_10000_value_hashmap.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if (count == 100000) {
                try(FileWriter writer = new FileWriter("src/results/remove_100000_id_hashmap.txt", true))
                {
                    writer.write(String.valueOf(num) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                try(FileWriter writer = new FileWriter("src/results/remove_100000_value_hashmap.txt", true))
                {
                    writer.write(String.valueOf(time) + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }


    public void addDefaultBus(int count) {
        for (int i = 0; i < count; i++) {
            Bus bus = new Bus();
            bus.setId(Model.getLogin());
            bus.setType(Controller.generateString(5));
            bus.setEquipment(Controller.generateString(5));
            bus.brand = Controller.generateString(10);
            bus.model = Controller.generateString(10);
            bus.maxLoad = Controller.random.nextDouble(1, 100);
            bus.maxPassengerCount = Controller.random.nextInt(1, 5);
            bus.maxSpeed = Controller.random.nextDouble(140, 300);

            buses.add(bus);
        }
    }
    public void addDefaultMotocycle(int count) {
        for (int i = 0; i < count; i++) {
            Motocycle motocycle = new Motocycle();
            motocycle.setId(Model.getLogin());
            motocycle.setType(Controller.generateString(5));
            motocycle.setEquipment(Controller.generateString(5));
            motocycle.brand = Controller.generateString(10);
            motocycle.model = Controller.generateString(10);
            motocycle.maxLoad = Controller.random.nextDouble(1, 100);
            motocycle.maxPassengerCount = Controller.random.nextInt(1, 5);
            motocycle.maxSpeed = Controller.random.nextDouble(140, 300);

            motocycles.add(motocycle);
        }
    }
    public void addDefaultTruck(int count) {
        for (int i = 0; i < count; i++) {
            Truck truck = new Truck();
            truck.setId(Model.getLogin());
            truck.setBodyType(Controller.generateString(5));
            truck.setEquipment(Controller.generateString(5));
            truck.setIsTrailer(false);
            truck.brand = Controller.generateString(10);
            truck.model = Controller.generateString(10);
            truck.maxLoad = Controller.random.nextDouble(1, 100);
            truck.maxPassengerCount = Controller.random.nextInt(1, 5);
            truck.maxSpeed = Controller.random.nextDouble(140, 300);

            trucks.add(truck);
        }
    }
    // </Other methods>
}
