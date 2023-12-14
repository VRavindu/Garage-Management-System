package lk.ijse.Model;

import javafx.scene.chart.XYChart;
import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.EmpAtndDto;
import lk.ijse.Dto.EmpSalaryDto;
import lk.ijse.Dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public boolean saveEmployee(final EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getNic());
        pstm.setString(4, dto.getAddress());
        pstm.setString(5, dto.getTel());
        pstm.setString(6, dto.getEmail());
        pstm.setString(7, dto.getJob());
        pstm.setString(8, dto.getDate());


        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }
    public boolean updateEmployee(final EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET name = ?, nic = ?, address = ?, tel = ?, email = ?, job = ?, date = ? WHERE id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getNic());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getTel());
        pstm.setString(5, dto.getEmail());
        pstm.setString(6, dto.getJob());
        pstm.setString(7, dto.getDate());
        pstm.setString(8, dto.getId());

        boolean isUpdate = pstm.executeUpdate() > 0;
        return isUpdate;
    }
    public boolean deleteEmployee(String id) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public EmployeeDto searchEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if (resultSet.next()) {
            String cId = resultSet.getString(1);
            String cName = resultSet.getString(2);
            String cNic = resultSet.getString(3);
            String cAddress = resultSet.getString(4);
            String cTel = resultSet.getString(5);
            String cEmail = resultSet.getString(6);
            String cJob = resultSet.getString(7);
            String cDate = resultSet.getString(8);

            dto = new EmployeeDto(cId, cName, cNic, cAddress, cTel, cEmail, cJob, cDate);
        }
        return dto;
    }
    public List<EmployeeDto> getAllEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<EmployeeDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String emp_id = resultSet.getString(1);
            String emp_name = resultSet.getString(2);
            String emp_nic = resultSet.getString(3);
            String emp_address = resultSet.getString(4);
            String emp_tel = resultSet.getString(5);
            String emp_email = resultSet.getString(6);
            String emp_job = resultSet.getString(7);
            String emp_date = resultSet.getString(8);

            var dto = new EmployeeDto(emp_id, emp_name, emp_nic, emp_address, emp_tel, emp_email, emp_job, emp_date);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public List<EmployeeDto> loadAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<EmployeeDto> empList = new ArrayList<>();

        while (resultSet.next()){
            empList.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }
        return empList;
    }

    public boolean AttendEmp(EmpAtndDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO attendance VALUES(?, ?, ?, ?, ?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getStatus());
        pstm.setString(4, dto.getTime());
        pstm.setString(5, String.valueOf(dto.getDate()));

        boolean isAtnd = pstm.executeUpdate() > 0;
        return isAtnd;
    }

    public List<EmpAtndDto> getAllAttendance() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM attendance";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<EmpAtndDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String nId = resultSet.getString(1);
            String nName = resultSet.getString(2);
            String nStatus = resultSet.getString(3);
            String nTime = resultSet.getString(4);
            String nDate = resultSet.getString(5);

            var dto = new EmpAtndDto(nId, nName, nStatus, nTime, nDate);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public String getWorkingDays(String id, int monthNumber) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM attendance WHERE e_id = ? AND MONTH(Date) = ? AND status = 'Present'";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);
        pstm.setString(2, String.valueOf(monthNumber));

        ResultSet resultSet = pstm.executeQuery();
        String dayCount = null;
        if (resultSet.next()){
            dayCount = resultSet.getString(1);
        }
        return dayCount;
    }

    public String generateNextSalaryId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT s_id FROM salary ORDER BY s_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String salId = null;
        if (resultSet.next()) {
            salId = resultSet.getString(1);
            return incrementSalaryId(salId);
        }
        return incrementSalaryId(null);
    }

    private String incrementSalaryId(String currentSalaryId) {    //O008
        if (currentSalaryId != null) {

            int id = Integer.parseInt(currentSalaryId);
            id++;
            return "000" + id;
        }
        return "0001";
    }

    public boolean saveSalary(EmpSalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO salary VALUES(?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getS_id());
        pstm.setInt(2, dto.getDays());
        pstm.setDouble(3, dto.getBonus());
        pstm.setDouble(4, dto.getDSalary());
        pstm.setDouble(5, dto.getTotal());
        pstm.setString(6, String.valueOf(dto.getDate()));
        pstm.setString(7, dto.getE_id());


        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }

    public List<EmpSalaryDto> getAllSalary() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<EmpSalaryDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String s_id = resultSet.getString(1);
            int day_count = resultSet.getInt(2);
            double bonus = resultSet.getDouble(3);
            double dSalary = resultSet.getDouble(4);
            double total = resultSet.getDouble(5);
            String date = resultSet.getString(6);
            String e_id = resultSet.getString(7);

            var dto = new EmpSalaryDto(s_id, day_count, bonus, dSalary, total, date, e_id);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public XYChart.Series getWorkingDaysChart() throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT COUNT(status), name FROM attendance WHERE MONTH(Date) AND status = 'Present' GROUP BY name";
            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();

            XYChart.Series series = new XYChart.Series();
            while (resultSet.next()){
                int count = resultSet.getInt(1);
                String emp = resultSet.getString(2);

                series.getData().add(new XYChart.Data(emp, count));

            }
            return series;
    }
}