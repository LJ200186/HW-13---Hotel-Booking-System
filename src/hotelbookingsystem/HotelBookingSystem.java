/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelbookingsystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HotelBookingSystem {

    private static Scanner Input = new Scanner(System.in);

    private static int SingleRooms = 12;
    private static int DoubleRooms = 6;
    private static int FamilyRooms = 2;

    private static int SingleRoomCost = 50;
    private static int DoubleRoomCost = 75;
    private static int FamilyRoomCost = 105;

    private static int SelfCateringCost = 0;
    private static int HalfBoardCost = 10;
    private static int FullBoardCost = 20;

    private static int RoomNumStart = 200;

    private static Dictionary Rooms = new Hashtable();
    private static Dictionary RoomCosts = new Hashtable();
    private static Dictionary BoardCosts = new Hashtable();

    public static void main(String[] args) {
        RoomSetup();
        MainMenu();
    }

    public static void MainMenu() {

        System.out.println("**ROOM BOOKING SYSTEM**\nWhat do you want to do?\n\n1 - Check-in\n2 - Check-out\n3 - View room details\n4 - View all rooms\n");
        int MenuSelection = Input.nextInt();

        switch (MenuSelection) {

            case 1:
                CheckIn();

            case 2:
                CheckOut();
                
            case 3:
                ViewRoomDetails();

            case 4:
                ViewAllRooms();
        }
    }
    
    public static void ViewRoomDetails(){
        System.out.println("\n**VIEW ROOM DETAILS**\nEnter room number\n");
        
        int RoomNum = Input.nextInt();
        Room CurrRoom = (Room) Rooms.get(RoomNum);
        
        if(CurrRoom.OccupiedTill.equals("")){
            
            System.out.println("\nRoom Details:\n- Type: "+CurrRoom.RoomType+"\n- Room is empty");
        }else{
            LocalDate CheckOutDate = LocalDate.parse(CurrRoom.OccupiedTill);
            LocalDate lt = LocalDate.now();
            long StayDuration = ChronoUnit.DAYS.between(lt, CheckOutDate);
            
            String ListOccupants = CurrRoom.Occupants.toString()
                    .replace("[", "")
                    .replace("]", "");
            
            System.out.println("\nRoom Details:\n- Room Type: " + CurrRoom.RoomType + "\n- Occupants Boarding Type: " + CurrRoom.OccupantsBoardType + "\n- Occupants: " + ListOccupants + "\n- Stay Duration Remaining: " + StayDuration + " days (Check-out: " + CurrRoom.OccupiedTill + ")");
        }
        
        System.out.println("\n");
        MainMenu();
        
    }

    public static void CheckOutRoom(int RoomNum, Room CurrRoom) {
        Room newRoom = new Room(RoomNum, CurrRoom.RoomType, "", (new ArrayList<>()), "");
        Rooms.put(RoomNum, newRoom);

        String ListOccupants = CurrRoom.Occupants.toString()
                .replace("[", "")
                .replace("]", "");

        System.out.println("\nSuccessfully checked-out room " + RoomNum + " and all occupants: " + ListOccupants);

    }

    public static void CheckOut() {
        System.out.println("\n**CHECK-OUT**\nEnter room number you want to check-out\n");
        int RoomNum = Input.nextInt();

        Room CurrRoom = (Room) Rooms.get(RoomNum);

        if (CurrRoom.OccupiedTill.equals("")) {

            System.out.println("This room is not occupied.");
            System.out.println("\n");
            MainMenu();

        } else {

            LocalDate CheckOutDate = LocalDate.parse(CurrRoom.OccupiedTill);
            LocalDate lt = LocalDate.now();
            long DaysLeft = ChronoUnit.DAYS.between(lt, CheckOutDate);

            if (DaysLeft > 0) {
                System.out.println("\nWARNING - The room you want to check-out is booked in for another " + DaysLeft + " days!\nOptions:\n\n1 - Continue\n2 - Cancel\n");
                int CheckoutOption = Input.nextInt();

                switch (CheckoutOption) {

                    case 1:
                        CheckOutRoom(RoomNum, CurrRoom);
                        System.out.println("\n");
                        MainMenu();

                    case 2:
                        System.out.println("\n");
                        MainMenu();
                }

            } else if (DaysLeft < 0) {

                DaysLeft = Long.parseLong((Long.toString(DaysLeft)).substring(1));

                int Cost = ((int) (RoomCosts.get(CurrRoom.RoomType)) + (int) (BoardCosts.get(CurrRoom.OccupantsBoardType))) * (int) DaysLeft;

                System.out.println("\nRoom has overstayed by " + DaysLeft + " days\nTo Pay: £" + Cost + "\nEnter 'Done' when payment complete...");
                String PaymentComplete = Input.next();
                System.out.println("");

                CheckOutRoom(RoomNum, CurrRoom);
                System.out.println("\n");
                MainMenu();
            } else {
                CheckOutRoom(RoomNum, CurrRoom);
                System.out.println("\n");
                MainMenu();
            }
        }

    }

    public static void getRoomsInfo(int Start, int End) {

        for (int i = (Start + 1); i <= End; i++) {

            Room CurrRoom = (Room) Rooms.get(i);

            String OccupiedTill = "Not Occupied";

            if (!(CurrRoom.OccupiedTill.equals(""))) {
                OccupiedTill = "Occupied till " + CurrRoom.OccupiedTill;
            }

            System.out.println("- Room " + i + ": " + OccupiedTill);
        }
    }

    public static void ViewAllRooms() {

        System.out.println("\n**VIEW ALL ROOMS**");

        System.out.println("\nSingle Rooms:");
        getRoomsInfo(RoomNumStart - 1, (RoomNumStart + SingleRooms));

        System.out.println("\nDouble Rooms:");
        getRoomsInfo((RoomNumStart + SingleRooms), (RoomNumStart + SingleRooms + DoubleRooms));

        System.out.println("\nFamily Rooms:");
        getRoomsInfo((RoomNumStart + SingleRooms + DoubleRooms), (RoomNumStart + SingleRooms + DoubleRooms + FamilyRooms));

        System.out.println("\n");
        MainMenu();
    }

    public static void CheckIn() {
        System.out.println("\n**CHECK-IN**\nSelect Room Type:\n\n1 - Single Room\n2 - Double Room\n3 - Family Room\n");
        int RoomSelection = Input.nextInt();

        String RoomType = "";
        String BoardType = "";
        int NumRoomOccupants = 0;
        ArrayList<String> RoomOccupants = new ArrayList<>();

        switch (RoomSelection) {

            case 1:
                RoomType = "SingleRoom";
                NumRoomOccupants = 1;
                break;
            case 2:
                RoomType = "DoubleRoom";
                NumRoomOccupants = 2;
                break;
            case 3:
                RoomType = "FamilyRoom";
                NumRoomOccupants = 4;
                break;
        }

        int RoomNumFound = -1;

        for (int i = 0; i < (SingleRooms + DoubleRooms + FamilyRooms); i++) {

            int RoomNum = RoomNumStart + i;

            Room CurrentRoom = (Room) Rooms.get(RoomNum);

            if (CurrentRoom.RoomType.equals(RoomType)) {
                if (CurrentRoom.OccupiedTill.equals("")) { // If room empty

                    RoomNumFound = RoomNum;
                    break;
                }
            }

        }

        if (RoomNumFound == -1) {
            System.out.println("Error: All " + RoomType + "s are occupied!");
            System.out.println("\n");
            MainMenu();
        } else {

            System.out.println("\nSelect boarding type:\n\n1 - Self-Catering\n2 - Half-Board\n3 - Full-Board\n");
            int BoardSelection = Input.nextInt();

            switch (RoomSelection) {

                case 1:
                    BoardType = "SelfCatering";
                    break;
                case 2:
                    BoardType = "HalfBoard";
                    break;
                case 3:
                    BoardType = "FullBoard";
                    break;
            }

            System.out.println("");
            Input.nextLine();

            for (int i = 1; i <= NumRoomOccupants;) {

                System.out.println("Enter occupant " + i + " full name");
                String Occupant = Input.nextLine();
                RoomOccupants.add(Occupant);
                i++;
            }

            System.out.println("\nEnter check-out date (Format: YYYY-MM-DD)");
            String StrCheckOutDate = Input.nextLine();

            LocalDate CheckOutDate = LocalDate.parse(StrCheckOutDate);
            LocalDate lt = LocalDate.now();
            long StayDuration = ChronoUnit.DAYS.between(lt, CheckOutDate);

            String ListOccupants = RoomOccupants.toString()
                    .replace("[", "")
                    .replace("]", "");

            System.out.println("\nBooking Details:\n- Room Type: " + RoomType + "\n- Boarding Type: " + BoardType + "\n- Occupants: " + ListOccupants + "\n- Stay Duration: " + StayDuration + " days (Check-out: " + StrCheckOutDate + ")");

            int Cost = 0;

            if (StayDuration > 7) {
                Cost = (int) (0.8 * (((int) (RoomCosts.get(RoomType)) + (int) (BoardCosts.get(BoardType))) * (int) (StayDuration - 7))) + (((int) (RoomCosts.get(RoomType)) + (int) (BoardCosts.get(BoardType))) * 7);
            } else {
                Cost = ((int) (RoomCosts.get(RoomType)) + (int) (BoardCosts.get(BoardType))) * (int) StayDuration;
            }

            System.out.println("\nTo Pay: £" + Cost + "\nEnter 'Done' when payment complete...");
            String PaymentComplete = Input.next();

            Room newRoom = new Room(RoomNumFound, RoomType, StrCheckOutDate, RoomOccupants, BoardType);
            Rooms.put(RoomNumFound, newRoom);
            System.out.println("\nSuccessfully checked into room " + RoomNumFound + "!");

            System.out.println("\n");
            MainMenu();
        }
    }

    public static void RoomSetup() {

        for (int i = 0; i <= (SingleRooms + DoubleRooms + FamilyRooms); i++) {

            String RoomType;

            if (i > (SingleRooms + DoubleRooms)) {
                RoomType = "FamilyRoom";
            } else if (i > SingleRooms) {
                RoomType = "DoubleRoom";
            } else {
                RoomType = "SingleRoom";
            }

            Room newRoom = new Room((RoomNumStart + i), RoomType, "", (new ArrayList<>()), "");

            Rooms.put((RoomNumStart + i), newRoom);
        }

        RoomCosts.put("SingleRoom", SingleRoomCost);
        RoomCosts.put("DoubleRoom", DoubleRoomCost);
        RoomCosts.put("FamilyRoom", FamilyRoomCost);
        BoardCosts.put("SelfCatering", SelfCateringCost);
        BoardCosts.put("HalfBoard", HalfBoardCost);
        BoardCosts.put("FullBoard", FullBoardCost);
    }

}
