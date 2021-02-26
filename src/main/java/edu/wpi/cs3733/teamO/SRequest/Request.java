package edu.wpi.cs3733.teamO.SRequest;

import edu.wpi.cs3733.teamO.Controllers.model.Node;
import edu.wpi.cs3733.teamO.UserTypes.Staff;
import java.util.Date;

public class Request {
  public String requestType;
  public Staff employeeAssigned;
  public Date date;
  public Date dateNeedBy;
  public String description;
  public Node locationNeeded;
}
