import java.util.*;

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 2);
        roomAvailability.put("Double", 2);
        roomAvailability.put("Suite", 1);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get(roomType) == null || availability.get(roomType) <= 0) {
            System.out.println("No rooms available for " + roomType + " for " + reservation.getGuestName());
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);

        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        assignedRoomsByType.get(roomType).add(roomId);

        inventory.updateAvailability(roomType, availability.get(roomType) - 1);

        System.out.println("Booking Confirmed:");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Allocated Room ID: " + roomId);
        System.out.println();
    }

    private String generateRoomId(String roomType) {

        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        String roomId = roomType.substring(0, 1).toUpperCase() + count;

        while (allocatedRoomIds.contains(roomId)) {
            count++;
            roomId = roomType.substring(0, 1).toUpperCase() + count;
        }

        return roomId;
    }
}

public class UseCase6RoomAllocation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        Queue<Reservation> queue = new LinkedList<>();

        queue.offer(new Reservation("Abhi", "Single"));
        queue.offer(new Reservation("Subha", "Double"));
        queue.offer(new Reservation("Vanmathi", "Suite"));
        queue.offer(new Reservation("Rahul", "Single"));

        while (!queue.isEmpty()) {
            service.allocateRoom(queue.poll(), inventory);
        }
    }
}