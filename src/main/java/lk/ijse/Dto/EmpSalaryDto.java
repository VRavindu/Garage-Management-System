package lk.ijse.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpSalaryDto {
    private String s_id;
    private int days;
    private double bonus;
    private double dSalary;
    private double total;
    private String date;
    private String e_id;
}
