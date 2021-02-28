package edu.wpi.cs3733.teamO.SRequest;

import edu.wpi.cs3733.teamO.UserTypes.Staff;

import java.util.Date;

public class Maintenence extends Request {
    public Maintenence(String requestID, Staff requestedBy, Date dateRequested, Date dateNeeded, String summary, String locationNodeID) {
        super(requestID, requestedBy, dateRequested, dateNeeded, summary, locationNodeID);
    }
}
