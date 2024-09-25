public abstract class Room {
    protected int roomID;
    protected int guestID;
    protected boolean roomStatus;
    protected String roomStatusDescription;
    protected String roomCategory;
    protected String roomType;
    protected double roomCostPerDay;
    protected int noOfDays;
    protected double roomTotalPrice;
    protected int noOfPeople;
    protected String guestName;
    protected String roomDescription;
    protected int maxNumberOfGuestPerRoom;
    protected int emergencyServicesCount;
    protected boolean specialRoom;

    public Room(int roomID, String roomType) {
        if (!roomType.equalsIgnoreCase("SINGLE") && !roomType.equalsIgnoreCase("DOUBLE") && !roomType.equalsIgnoreCase("TRIPLE")) {
            throw new IllegalArgumentException("Wrong Room Type!");
        }
        this.roomID = roomID;
        this.guestID = 0;
        this.guestName = null;
        this.roomType = roomType;
        this.roomCostPerDay = 0;
        this.roomTotalPrice = 0;
        this.noOfPeople = 0;
        this.noOfDays = 0;
        this.roomStatus = false;
        this.roomStatusDescription = "Available";
        this.specialRoom = false;
    }

    protected abstract double totalCostOfStay();
    protected abstract void determinePrice();
    public void checkInRoom(int guestID, int numDays) {
        this.roomStatus = true;
        this.roomStatusDescription = "Booked";
        this.guestID = guestID;
        this.noOfDays = numDays;
        this.roomTotalPrice = totalCostOfStay();
    }
    public void checkOutRoom(){
        this.guestID = 0;
        this.roomStatus = false;
        this.roomStatusDescription = "Available";
        this.noOfDays = 0;
        this.roomTotalPrice = 0;
        this.noOfPeople = 0;
        guestName = null;
    }

    @Override
    public String toString() {
        return  "Room ID: " + roomID +
                "\tGuest ID: " + guestID +
                "\t\tRoom Status: " + roomStatusDescription +
                "\t\tRoom Category: " + roomCategory +
                "\t\tRoom Type: " + roomType +
                "\t\tRoom Cost:" + roomCostPerDay +
                "\t\tDays Staying: " + noOfDays +
                "\t\tTotal Price: " + roomTotalPrice +
                "\t\tNo Of People: " + noOfPeople +
                "\t\tName: " + guestName;
    }

    //Setters
    public void setRoomCostPerDay(double roomCostPerDay) {
        this.roomCostPerDay = roomCostPerDay;
    }
    public void setMaxNumberOfGuestPerRoom(int maxNumberOfGuestPerRoom) {
        this.maxNumberOfGuestPerRoom = maxNumberOfGuestPerRoom;
    }
    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }
    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }
    public void setRoomTotalPrice(double roomTotalPrice) {
        this.roomTotalPrice = roomTotalPrice;
    }
    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
    public void setRoomStatus(boolean roomStatus) {
        this.roomStatus = roomStatus;
    }
    public void setRoomStatusDescription(String roomStatusDescription) {
        this.roomStatusDescription = roomStatusDescription;
    }

    //Getters
    public boolean isRoomStatus() {
        return roomStatus;
    }
    public String getRoomCategory() {
        return roomCategory;
    }
    public String getRoomType() {
        return roomType;
    }
    public int getRoomID() {
        return roomID;
    }
    public int getGuestID() {
        return guestID;
    }
    public int getNoOfDays() {
        return noOfDays;
    }
    public double getRoomTotalPrice() {
        return roomTotalPrice;
    }
    public int getNoOfPeople() {
        return noOfPeople;
    }
    public String getGuestName() {
        return guestName;
    }
    public boolean isSpecialRoom() {
        return specialRoom;
    }


    public void printReceipt(){
        String formattedString =
                "------------RECEIPT-------------\n"+
                "Guest Name: " + guestName + "\n" +
                "Room Category: " + roomCategory + "\n" +
                "Room Type: " + roomType + "\n" +
                "Room ID: " + roomID + "\n" +
                "Room Price Per Day: " + roomCostPerDay + "\n" +
                "Number of Days Staying: " + noOfDays + "\n" +
                "Total Cost of the Room: " + roomTotalPrice + "\n" +
                "Guest ID: " + guestID+ "\n" +
                "Room Description: " + roomDescription;

        System.out.println(formattedString);

    }
}
