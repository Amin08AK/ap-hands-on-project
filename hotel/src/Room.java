public class Room {

    private String roomType;
    private boolean available;
    private int guestReservedThisRoomIndex;

    public int getGuestReservedThisRoomIndex() {
        return guestReservedThisRoomIndex;
    }

    public void setGuestReservedThisRoomIndex(int guestReservedThisRoomIndex) {
        this.guestReservedThisRoomIndex = guestReservedThisRoomIndex;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    private boolean isEmpty;
    private long price;

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Room(){
        setEmpty(true);
        setAvailable(true);
    }


    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public void roomInfo(){
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Price Per Night : " + getPrice());
        String print = isEmpty()==true ? "This Room is Empty" : "This room is reserved";
        System.out.println(print);
        System.out.println("Guest Index reserved this room: "+getGuestReservedThisRoomIndex());

    }

}
