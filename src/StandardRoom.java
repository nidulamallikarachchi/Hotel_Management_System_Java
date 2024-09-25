public class StandardRoom extends Room{
    public StandardRoom(int roomID, String roomType) {
        super(roomID, roomType);
        this.roomCategory = "STANDARD";
        this.roomDescription = "Standard Room With Basic Amenities";
        determinePrice();

    }

    @Override
    protected void determinePrice() {
        if(roomType == "SINGLE"){
            setRoomCostPerDay(230);
        }
        else if (roomType =="DOUBLE") {
            setRoomCostPerDay(280);
        }
        else if (roomType == "TRIPLE"){
            setRoomCostPerDay(350);
        }
    }

    @Override

    public double totalCostOfStay() {
        return roomCostPerDay*noOfDays;
    }
}
