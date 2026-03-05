
public class ParkingLot {

    static class Vehicle {
        String licensePlate;
        long entryTime;

        Vehicle(String licensePlate) {
            this.licensePlate = licensePlate;
            this.entryTime = System.currentTimeMillis();
        }
    }

    static final int SIZE = 500;
    static Vehicle[] table = new Vehicle[SIZE];
    static int occupied = 0;
    static int totalProbes = 0;

    static int hash(String key) {
        return Math.abs(key.hashCode()) % SIZE;
    }

    static void parkVehicle(String licensePlate) {
        int index = hash(licensePlate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % SIZE;
            probes++;
        }

        table[index] = new Vehicle(licensePlate);
        occupied++;
        totalProbes += probes;

        System.out.println("Vehicle " + licensePlate +
                " parked at spot #" + index +
                " (" + probes + " probes)");
    }

    static void exitVehicle(String licensePlate) {
        int index = hash(licensePlate);

        while (table[index] != null) {
            if (table[index].licensePlate.equals(licensePlate)) {

                long exitTime = System.currentTimeMillis();
                long durationMillis = exitTime - table[index].entryTime;
                double hours = durationMillis / (1000.0 * 60 * 60);
                double fee = hours * 5;

                table[index] = null;
                occupied--;

                System.out.println("Vehicle " + licensePlate + " exited.");
                System.out.println("Duration: " +
                        String.format("%.2f", hours) +
                        " hours, Fee: $" +
                        String.format("%.2f", fee));
                return;
            }
            index = (index + 1) % SIZE;
        }

        System.out.println("Vehicle not found!");
    }

    static void findNearestSpot() {
        for (int i = 0; i < SIZE; i++) {
            if (table[i] == null) {
                System.out.println("Nearest available spot: #" + i);
                return;
            }
        }
        System.out.println("Parking Full!");
    }

    static void getStatistics() {
        double loadFactor = (double) occupied / SIZE;
        double avgProbes = occupied == 0 ? 0 : (double) totalProbes / occupied;

        System.out.println("Occupancy: " +
                String.format("%.2f", loadFactor * 100) + "%");
        System.out.println("Average Probes: " +
                String.format("%.2f", avgProbes));
    }

    public static void main(String[] args) {

        parkVehicle("ABC-1234");
        parkVehicle("ABC-1235");
        parkVehicle("XYZ-9999");

        findNearestSpot();

        getStatistics();

        exitVehicle("ABC-1234");

        getStatistics();
    }
}
