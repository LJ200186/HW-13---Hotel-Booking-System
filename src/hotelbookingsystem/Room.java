/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelbookingsystem;

import java.util.*;


public class Room {
    
    public int RoomNum;
    public String RoomType;
    
    
    public String OccupiedTill = "";
    public ArrayList<String> Occupants = new ArrayList<>();
    public String OccupantsBoardType = "";

    public Room(int RoomNum, String RoomType, String OccupiedTill, ArrayList Occupants, String OccupantsBoardType) {
        this.RoomNum = RoomNum;
        this.RoomType = RoomType;
        this.OccupiedTill = OccupiedTill;
        this.Occupants = Occupants;
        this.OccupantsBoardType = OccupantsBoardType;
                
    }
    
    
    
}
