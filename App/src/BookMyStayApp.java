import java.util.*;

class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 2);
        roomAvailability.put("Double", 1);
        roomAvailability.put("Suite", 1);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

class CancellationService {

    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation: Reservation not found");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        releasedRoomIds.push(reservationId);

        Map<String, Integer> availability = inventory.getRoomAvailability();
        inventory.updateAvailability(roomType, availability.get(roomType) + 1);

        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully for ID: " + reservationId);
    }

    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most recent first):");

        while (!releasedRoomIds.isEmpty()) {
            System.out.println(releasedRoomIds.pop());
        }
    }
}

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        service.registerBooking("R1", "Single");
        service.registerBooking("R2", "Double");
        service.registerBooking("R3", "Suite");

        service.cancelBooking("R2", inventory);
        service.cancelBooking("R3", inventory);

        service.showRollbackHistory();

        System.out.println("\nUpdated Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}