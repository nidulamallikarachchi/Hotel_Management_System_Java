public class SpecialDouble extends StandardRoom implements SpecialServices{
    protected int emergencyNumber;

    public SpecialDouble(int roomID, String roomType) {
        super(roomID, roomType);
        this.roomDescription = "Special Double Room With Fitted Ramps, Emergency Calling Facilities & Close to Beach";
        this.emergencyNumber = 911;
        this.specialRoom = true;
    }

    @Override
    public void provideAssistance() {

    }

    @Override
    public void callEmergencyServices() {
        emergencyServicesCount++;
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
                "\t\tName: " + guestName +
                "\t\tSPECIAL DOUBLE ROOM"
                ;

    }

    @Override
    public void printReceipt(){
        String formattedString =
                "------------RECEIPT-------------\n"+
                "Guest Name: " + guestName + "\n" +
                "Room Category: " + roomCategory + " SPECIAL "+"\n" +
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
