public class PremiumRoom extends Room{
    public PremiumRoom(int roomID, String roomType) {
        super(roomID, roomType);
        this.roomCategory = "PREMIUM";
        this.roomDescription = "Premium Room with Spa Area and a Kitchenette";
        determinePrice();
    }

    @Override
    protected void determinePrice() {
        if(roomType == "SINGLE"){
            setRoomCostPerDay(500);
        }
        else if (roomType =="DOUBLE") {
            setRoomCostPerDay(600);
        }
        else if (roomType == "TRIPLE"){
            setRoomCostPerDay(690);
        }
    }

    @Override
    public double totalCostOfStay() {
        return roomCostPerDay*noOfDays;
    }
}
