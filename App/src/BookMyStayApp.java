import java.util.*;

class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {

    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {

        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
        servicesByReservation.get(reservationId).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = servicesByReservation.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }

    public void displayServices(String reservationId) {

        List<AddOnService> services = servicesByReservation.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Selected Services:");
        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " : " + s.getCost());
        }
    }
}

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        String reservationId = "R101";

        AddOnServiceManager manager = new AddOnServiceManager();

        AddOnService s1 = new AddOnService("Breakfast", 500);
        AddOnService s2 = new AddOnService("Spa", 1500);
        AddOnService s3 = new AddOnService("Airport Pickup", 800);

        manager.addService(reservationId, s1);
        manager.addService(reservationId, s2);
        manager.addService(reservationId, s3);

        System.out.println("Add-On Services for Reservation: " + reservationId);
        manager.displayServices(reservationId);

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("\nTotal Add-On Cost: " + totalCost);
    }
}