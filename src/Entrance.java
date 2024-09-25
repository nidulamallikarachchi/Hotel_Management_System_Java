import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Entrance {

    //-------------------------------------------------IMPORTANT-------------------------------------------------------------
    ArrayList<Room> accommodations;

    public static Entrance entrance = new Entrance();
    public static Scanner scan = new Scanner(System.in);

    //INITIALIZATION
    public Entrance() {
        this.accommodations = new ArrayList<>();
        this.roomRecommendations = new ArrayList<>();
    }
    public void initializeHotel() {
        Room room;
        int roomNumber = 101;

        int standardRoomCount = 0, deluxeRoomCount = 0, premiumRoomCount = 0;

        while (standardRoomCount < 12) {
            if (standardRoomCount < 4) {
                room = new StandardRoom(roomNumber, "SINGLE");
                room.setMaxNumberOfGuestPerRoom(2);
            } else if (standardRoomCount < 8) {
                room = new StandardRoom(roomNumber, "DOUBLE");
                room.setMaxNumberOfGuestPerRoom(4);
            } else {
                room = new StandardRoom(roomNumber, "TRIPLE");
                room.setMaxNumberOfGuestPerRoom(6);
            }
            accommodations.add(room);
            standardRoomCount++;
            roomNumber++;
        }

        while (deluxeRoomCount < 12) {
            if (deluxeRoomCount < 4) {
                room = new DeluxeRoom(roomNumber, "SINGLE");
                room.setMaxNumberOfGuestPerRoom(2);
            } else if (deluxeRoomCount < 8) {
                room = new DeluxeRoom(roomNumber, "DOUBLE");
                room.setMaxNumberOfGuestPerRoom(4);
            } else {
                room = new DeluxeRoom(roomNumber, "TRIPLE");
                room.setMaxNumberOfGuestPerRoom(6);
            }
            accommodations.add(room);
            deluxeRoomCount++;
            roomNumber++;
        }

        while (premiumRoomCount < 12) {
            if (premiumRoomCount < 4) {
                room = new PremiumRoom(roomNumber, "SINGLE");
                room.setMaxNumberOfGuestPerRoom(2);
            } else if (premiumRoomCount < 8) {
                room = new PremiumRoom(roomNumber, "DOUBLE");
                room.setMaxNumberOfGuestPerRoom(4);
            } else {
                room = new PremiumRoom(roomNumber, "TRIPLE");
                room.setMaxNumberOfGuestPerRoom(6);
            }
            accommodations.add(room);
            premiumRoomCount++;
            roomNumber++;
        }


        room = new SpecialDouble(roomNumber,"DOUBLE");
        accommodations.add(room);

        roomNumber++;

        room = new SpecialTriple(roomNumber,"TRIPLE");
        accommodations.add(room);

    }
    public void readFromFile(){
        File file = new File("Database.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("Database.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line using "\t|\t" as the delimiter
                String[] elements = line.split("\t\\|\t");

                // Check if the element is not empty before parsing
                if (!elements[0].trim().isEmpty()) {
                    int roomID = Integer.parseInt(elements[0].trim());
                    int guestID = Integer.parseInt(elements[1].trim());
                    int noOfDays = Integer.parseInt(elements[2].trim());
                    double roomTotalPrice = Double.parseDouble(elements[3].trim());
                    int noOfPeople = Integer.parseInt(elements[4].trim());
                    String guestName = elements[5].trim();

                    // Use the variables as needed
                    Room room = selectRoomByRoomID(roomID);
                    if (room != null) {
                        room.setGuestID(guestID);
                        room.setNoOfDays(noOfDays);
                        room.setRoomTotalPrice(roomTotalPrice);
                        room.setNoOfPeople(noOfPeople);
                        room.setGuestName(guestName);
                        room.setRoomStatus(true);
                        room.setRoomStatusDescription("Booked");
                    } else {
                        // Handle the case where the room with the specified ID is not found
                        System.out.println("Room with ID " + roomID + " not found.");
                    }
                } else {
                    // Handle the case where the string is empty (if needed)
                    System.out.println("Skipping empty line.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //DISPLAY ROOMS
    public void displayAllRooms() {
        for (Room room : accommodations) {
            if (room.isRoomStatus()) {
                System.out.println(redColor + room + resetColor);
            } else {
                System.out.println(greenColor + room + resetColor);
            }
        }
    }
    public void displayAvailableRooms(){
        for(Room room: accommodations){
            if(!room.isRoomStatus()){
                System.out.println(greenColor+room+resetColor);
            }
        }
    }
    public void displayBookedRooms(){
        for(Room room: accommodations){
            if(room.isRoomStatus()){
                System.out.println(redColor+room+resetColor);
            }
        }
    }

    //CHECK-IN ROOM
    public void checkInRoom(Room room, String guestName, int noOfPeople, int noOfDays, int guestID){
        room.setNoOfPeople(noOfPeople);
        room.setGuestName(guestName);
        room.checkInRoom(guestID,noOfDays);
//        noOfRoomsBooked++;
//        totalIncomeOfHotel+=room.totalCostOfStay();
    }
    public boolean validateCheckInRoom(Room room) throws AccomodationException{
        boolean isAvailable = false;
        try {
            if(!room.isRoomStatus()){
                isAvailable = true;
            }
            else {
                throw new AccomodationException("This room is Already Booked!");
            }
        }catch (AccomodationException e){
            System.out.println(e.getMessage());
        }
        return isAvailable; } //User Tries to Book a Room That has Already Been Booked

    //CHECK-OUT ROOM
    public boolean validateRoomCheckOut(Room room) throws AccomodationException {
        boolean isBooked = false;
        try{
            if(room.isRoomStatus()){
                isBooked = true;
            }
            else {
                throw new AccomodationException("This Room is Not Booked!");
            }
        }catch (AccomodationException e){
            System.out.println(e.getMessage());
        }

        return isBooked;
    } //User Tires to Check Out a Room That has noot been booked before

    //OBJECT PERSISTENCE
    public void writeToFile(){
        StringBuilder lines = new StringBuilder();

        for (Room room : accommodations) {
            if (room.isRoomStatus()) {
                String a = "\t|\t";
                String line = room.getRoomID() + a +
                        room.getGuestID() + a +
                        room.getNoOfDays() + a +
                        room.getRoomTotalPrice() + a +
                        room.getNoOfPeople() + a +
                        room.getGuestName();

                lines.append(line).append(System.lineSeparator()); // Use System.lineSeparator() for cross-platform newline
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Database.txt"))) {
            writer.write(lines.toString());
            System.out.println("Data written to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //BASIC UTILITIES
    public Room selectRoomByRoomID(int roomID) {
        Room targetRoom = null;
        for (Room room : accommodations) {
            if (roomID == room.getRoomID()) {
                targetRoom = room;
            }
        }
        if (targetRoom == null) {
            System.out.println("No Room Found!");
        }
        return targetRoom;
    }

    //MAIN METHODS
    public void case2_1_BookingANormalRoom() throws AccomodationException {

        boolean specialRoom = false;

        scan.nextLine();

        System.out.print("Enter Guest Name: ");
        String guestName = scan.nextLine();

        // scan.nextLine(); // Remove this line
        System.out.print("Enter Number of Days of Stay: ");
        int noOfDays = scan.nextInt();
        scan.nextLine(); // Add this here to consume the newline after the integer input


        int noOfPeople;
        do{
            System.out.print("Enter Number of People Staying: ");
            noOfPeople = scan.nextInt();
            scan.nextLine();
            if(noOfPeople<=0){
                System.out.println("Enter Valid Number!");
            }
            if(noOfPeople>6){
                System.out.println("Maximum Accommodation is 6 People per Room");
            }
        }while (noOfPeople<=0 || noOfPeople>6);

        String roomType;
        do{
            do{
                System.out.print("Enter Room Type: ");
                roomType = scan.nextLine();
            }while (!entrance.validateRoomType(roomType));
        }while (!entrance.validateNoOfGuestVsRoomTypeForNormalRoom(roomType,noOfPeople));

        String roomCategory;
        do{
            System.out.print("Enter Room Category: ");
            roomCategory = scan.nextLine();
        }while (!entrance.validateRoomCategory(roomCategory));

        entrance.normalRoomRecommendations(roomCategory,roomType);
        entrance.displayRecommendedRooms();

        int roomID;
        Room room;
        do{
            do{
                System.out.print("Enter Room ID: ");
                roomID = scan.nextInt();
            }while (!entrance.validateRoomRecommendations(roomID));
            room = entrance.selectRoomByRoomID(roomID);
        }while (!entrance.validateCheckInRoom(room));

        int guestID = roomID+100;

        room = entrance.selectRoomByRoomID(roomID);

        entrance.checkInRoom(room, guestName, noOfPeople, noOfDays, guestID);

        entrance.roomRecommendations.clear();

        System.out.println(greenColor);
        room.printReceipt();
        System.out.println(resetColor);


    }
    public void case2_2_BookingASpecialRoom() throws AccomodationException {

        String roomType;

        scan.nextLine();
        do{
            System.out.print("Enter Room Type: ");
            roomType = scan.nextLine();
        }while (!entrance.validateSpecialRoomType(roomType));


        int noOfPeople = 0;

        do{
            do{
                System.out.print("Enter Number of People Staying: ");
                noOfPeople = scan.nextInt();
                if(noOfPeople<=0){
                    System.out.println("Enter a Valid Number!");
                }
                if(noOfPeople>6){
                    System.out.println("No More than 6 People Can Stay in a Room!");
                }
            }while (noOfPeople<0 ||noOfPeople>6);
        }while (!entrance.validateSpecialRoomVsNoOfGuest(roomType,noOfPeople));

        Room room;

        if(entrance.specialRoomRecommendations(roomType)){
            entrance.displayRecommendedRooms();

            int roomID;
            do{
                do{
                    System.out.print("Enter Room ID to Book Room: ");
                    roomID =  scan.nextInt();
                    room = entrance.selectRoomByRoomID(roomID);
                }while (!entrance.validateRoomRecommendations(roomID));
            }while (!entrance.validateCheckInRoom(room));

            scan.nextLine();

            System.out.print("Enter Guest Name: ");
            String guestName = scan.nextLine();

            System.out.print("Enter Number of Days Staying: ");
            int noOfDays = scan.nextInt();

            int guestID = roomID+100;

            entrance.checkInRoom(room,guestName,noOfPeople,noOfDays,guestID);

            System.out.println(greenColor);
            room.printReceipt();
            System.out.println(resetColor);
        }
    }
    public void case3_CheckOutRoom() throws AccomodationException {

        entrance.displayBookedRooms();

        Room room;
        int roomID;
        do{
            do{
                System.out.print("Enter Room ID: ");
                roomID = scan.nextInt();
            }while (!entrance.validateRoomID(roomID));

            room = entrance.selectRoomByRoomID(roomID);
        }while (!entrance.validateRoomCheckOut(room));

        room.checkOutRoom();
        noOfRoomsBooked--;
    }
    public void case4_calculateTotalIncome() {
        double totalIncome = 0;
        for (Room room : accommodations) {
            totalIncome = 0;
            if (room.isRoomStatus()) {
                totalIncome += room.getRoomTotalPrice();
            }
        }
        System.out.println("Total Income of Hotel: " + totalIncome);
    }

    //MAIN
    public static void main(String[] args) throws AccomodationException {

        entrance.initializeHotel();
        entrance.readFromFile();

        while (true) {
            System.out.println("Hotel Management System Menu:");
            System.out.println("1. Display Rooms");
            System.out.println("2. Check In Rooms");
            System.out.println("3. Check Out Rooms");
            System.out.println("4. Display Statistical Report");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = scan.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Displaying Rooms\n");
                    System.out.println("Display Rooms Submenu:");
                    System.out.println("\t1. Display All Rooms");
                    System.out.println("\t2. Display Available Rooms");
                    System.out.println("\t3. Display Booked Rooms");
                    System.out.println("\t4. Back to Main Menu");
                    System.out.print("Enter your choice (1-4): ");

                    int subChoice = scan.nextInt();

                    switch (subChoice) {
                        case 1:
                            System.out.println("Displaying All Rooms");
                            entrance.displayAllRooms();
                            break;
                        case 2:
                            System.out.println("Displaying Available Rooms");
                            entrance.displayAvailableRooms();
                            break;
                        case 3:
                            System.out.println("Displaying Booked Rooms");
                            entrance.displayBookedRooms();
                            break;
                        case 4:
                            return;
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                    }
                    break;
                case 2:
                    System.out.println("Checking In Rooms\n");
                    System.out.println("Check In Submenu:");
                    System.out.println("\t1. Book a Normal Room");
                    System.out.println("\t2. Book a Special Room");
                    System.out.println("\t3. Back to Main Menu");
                    System.out.print("Enter your choice (1-3): ");

                    subChoice = scan.nextInt();

                    switch (subChoice) {
                        case 1:
                            entrance.case2_1_BookingANormalRoom();
                            break;
                        case 2:
                            entrance.case2_2_BookingASpecialRoom();
                            break;
                        case 3:
                            return; // Return to the main menu
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    }
                    break;
                case 3:
                    entrance.case3_CheckOutRoom();
                    break;
                case 4:
                    // Call a method to display statistical report
                    System.out.println(greenColor);
                    System.out.println("Displaying Statistical Report...");
                    entrance.case4_calculateTotalIncome();
                    System.out.println(resetColor);
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    entrance.writeToFile();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    //--------------------------------------------------EXTRA WORK------------------------------------------------------------

    String redColor = "\u001B[31m";
    static String greenColor = "\u001B[38;2;11;244;102m";
    static String resetColor = "\u001B[0m";

    ArrayList<Room> roomRecommendations;
    public void displayRecommendedRooms(){
        for(Room room: roomRecommendations){
            if (!room.isRoomStatus()) {
                System.out.println(greenColor + room + resetColor);
            }
        }
    }
    public void normalRoomRecommendations(String roomCategory, String roomType){
        boolean roomsFound = false;

        roomRecommendations.clear();

        for (Room room : accommodations) {
            if (roomCategory.equalsIgnoreCase(room.getRoomCategory()) && roomType.equalsIgnoreCase(room.getRoomType())) {
                if(!room.isRoomStatus() && !room.isSpecialRoom()){
                    roomRecommendations.add(room);
                    roomsFound = true;  // Update the flag when a matching room is found
                }else {
                    if(!room.isSpecialRoom()){
                        roomRecommendations.add(room);
                    }
                    // booked rooms are also added to handel exceptions here
                }
            }
        }

        if (!roomsFound) {
            boolean roomsFound2 = false;

            System.out.println("This Type of Room is Not Available at The Moment, Consider Following Recommended Rooms!");

            if (roomType.equalsIgnoreCase("SINGLE")) {
                for (Room room : accommodations) {
                    if (room.getRoomType().equalsIgnoreCase("SINGLE") ||
                            room.getRoomType().equalsIgnoreCase("DOUBLE") ||
                            room.getRoomType().equalsIgnoreCase("TRIPLE")) {
                        if (!room.isRoomStatus() && !room.isSpecialRoom()) {
                            roomRecommendations.add(room);
                            roomsFound2 = true;
                        }
                    }
                }
            }

            if (roomType.equalsIgnoreCase("DOUBLE")) {
                for (Room room : accommodations) {
                    if (room.getRoomType().equalsIgnoreCase("DOUBLE") ||
                            room.getRoomType().equalsIgnoreCase("TRIPLE")) {
                        if (!room.isRoomStatus() && !room.isSpecialRoom()) {
                            roomRecommendations.add(room);
                            roomsFound2 = true;
                        }
                    }
                }
            }

            if (roomType.equalsIgnoreCase("TRIPLE")) {
                for (Room room : accommodations) {
                    if (room.getRoomType().equalsIgnoreCase("TRIPLE")) {
                        if (!room.isRoomStatus() && !room.isSpecialRoom()) {
                            roomRecommendations.add(room);
                            roomsFound2 = true;
                        }
                    }
                }
            }

            if (!roomsFound2) {
                System.out.println("No recommended rooms available.");
            }
        }
    }
    public boolean specialRoomRecommendations(String roomType) {
        boolean isValid = false;
        boolean roomsAvailable = false;

        // Clear previous recommendations
        roomRecommendations.clear();

        for (Room room : accommodations) {
            if (roomType.equalsIgnoreCase(room.getRoomType())) {
                if (room.isSpecialRoom() && !room.isRoomStatus()) {
                    roomRecommendations.add(room);
                    roomsAvailable = true;
                    isValid = true;
                }
            }
        }

        if (!roomsAvailable) {
            for (Room room : accommodations) {
                if (room.isSpecialRoom() && !room.isRoomStatus()) {
                    System.out.println("The Required Room Type is Not Available! We Recommend the Following Rooms!");
                    isValid = true;
                    roomRecommendations.add(room);
                }
            }
        }

        if (!roomsAvailable && roomRecommendations.isEmpty()) {
            System.out.println("Sorry, We are completely Out of Special Rooms!");
            isValid = false;
        }

        return isValid;
    }

    public static int noOfRoomsBooked;

    //VALIDATIONS
    public boolean validateRoomType(String roomType){
        boolean valid = false;
        try {
            if(roomType.equalsIgnoreCase("SINGLE") || roomType.equalsIgnoreCase("DOUBLE") || roomType.equalsIgnoreCase("TRIPLE")){
                valid = true;
            }
            else {
                throw new IllegalArgumentException("Enter Valid Input (SINGLE | DOUBLE | TRIPLE) ");
            }
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return valid;
    }
    public boolean validateRoomCategory(String roomCategory){
        boolean valid = false;
        try {
            if(roomCategory.equalsIgnoreCase("STANDARD") || roomCategory.equalsIgnoreCase("DELUXE") || roomCategory.equalsIgnoreCase("PREMIUM")){
                valid = true;
            }
            else {
                throw new IllegalArgumentException("Enter Valid Input (STANDARD | DELUXE | PREMIUM) ");
            }
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return valid;

        //        String roomCategory;
//        do{
//            System.out.print("Enter Room Category: ");
//            roomCategory = scan.nextLine();
//        }while (!entrance.validateRoomCategory(roomCategory));
    }
    public boolean validateRoomID(int roomID){
        boolean valid = false;
        try {
            for (Room room: accommodations){
                if(roomID == room.getRoomID()){
                    valid = true;
                    break;
                }
            }
            if(!valid){
                throw new IndexOutOfBoundsException("Invalid Room ID");
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        return valid;
    } //User Enters an invalid Room ID
    public boolean validateRoomRecommendations(int roomID){
        Room room = selectRoomByRoomID(roomID);
        boolean isValid = false;
        try {
            if(roomRecommendations.contains(room)){
                isValid = true;
            }else {
                throw new IndexOutOfBoundsException("Choose a Recommended Room!");
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        return isValid;} //User Tries to Book a Room Out of the Recommended Range
    public boolean validateNoOfGuestVsRoomTypeForNormalRoom(String roomType, int noOfPeople){
        boolean isValid = true;
        //If Number of People are greater than 2 && Room Type selected is Single
        boolean a = roomType.equalsIgnoreCase("Single") && (noOfPeople>2 && noOfPeople<=4);
        //if Number of People are greater than 4 && Room Type Selected is Single or Double
        boolean b = (roomType.equalsIgnoreCase("Single") || roomType.equalsIgnoreCase("Double")) && (noOfPeople>4 && noOfPeople<=6);

        try{
            if(a){
                isValid = false;
                throw new IndexOutOfBoundsException(noOfPeople + " People Cannot Book a "+roomType.toUpperCase() +" Room");
            } else if (b) {
                isValid = false;
                throw new IndexOutOfBoundsException(noOfPeople + " People Cannot Book a "+roomType.toUpperCase() +" Room");
            }
        }
        catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        return isValid;
    }
    public boolean validateSpecialRoomType(String roomType) {
        boolean isValid = false;
        try{
            if (roomType.equalsIgnoreCase("DOUBLE") || roomType.equalsIgnoreCase("TRIPLE")) {
                isValid = true;
            } else if (roomType.equalsIgnoreCase("SINGLE")) {
                throw new IllegalArgumentException("There are No Special Single Rooms!");
            } else {
                throw new IllegalArgumentException("Enter Valid Room Type");
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return isValid;
    }
    public boolean validateSpecialRoomVsNoOfGuest(String roomType, int noOfPeople){
        boolean isValid = true;

        try{
            //Can't be less than 2 people
            if(noOfPeople<2){
                isValid = false;
                throw new IndexOutOfBoundsException("At Least 2 People Should Stay in a Special Room!");
            }

            //if a double room cant be more than 4 people
            if(roomType.equalsIgnoreCase("double")&& noOfPeople>4){
                isValid = false;
                throw new IndexOutOfBoundsException(noOfPeople+" People Cannot Stay in a Double Room");
            }

            //if a triple room can't be more than 6 people
            if(roomType.equalsIgnoreCase("triple") && noOfPeople>6){
                isValid = false;
                throw new IndexOutOfBoundsException(noOfPeople + " People cannot stay in a Triple Room");
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }

        return isValid;
    }

}