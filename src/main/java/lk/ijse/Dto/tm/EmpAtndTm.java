package lk.ijse.Dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpAtndTm {
    private String id;
    private String name;
    private String status;
    private String time;
    private String date;
}
