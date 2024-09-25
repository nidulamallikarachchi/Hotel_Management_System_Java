public class DeluxeRoom extends Room{
    public DeluxeRoom(int roomID, String roomType) {
        super(roomID, roomType);
        this.roomCategory = "DELUXE";
        this.roomDescription = "Deluxe Rooms with More Space and a Bathtub";
        determinePrice();
    }

    @Override
    protected void determinePrice() {
        if(roomType == "SINGLE"){
            setRoomCostPerDay(350);
        }
        else if (roomType =="DOUBLE") {
            setRoomCostPerDay(430);
        }
        else if (roomType == "TRIPLE"){
            setRoomCostPerDay(500);
        }
    }

    @Override
    public double totalCostOfStay() {
        return roomCostPerDay*noOfDays;
    }
}
