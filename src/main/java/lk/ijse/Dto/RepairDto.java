package lk.ijse.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RepairDto {
    private String id;
    private String status;
    private String cost;
    private String plateNo;
    private String empId;
    private String date;
}
