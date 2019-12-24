import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class select {

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{

			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "af1iass2");

			//Execute a query
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select name from instructor where dept_name = 'Comp. Sci.' and salary > 70000");

			//Print results
            while(rs.next()) {
            	System.out.println(rs.getString("name"));

            }
            
            System.out.println("------------------");
            System.out.println("select  name, course_id from    instructor, teaches where  instructor.ID = teaches.ID   and instructor.dept_name = 'Biology'");
            System.out.println("------------------");

            rs = stmt.executeQuery("select  name, course_id from    instructor, teaches where  instructor.ID = teaches.ID   and instructor.dept_name = 'Biology'");
            while(rs.next()) {
            	System.out.println(rs.getString("name")+ " | " + rs.getString("course_id"));
            }


            // close a connection
            stmt.close();
            conn.close();

		}catch (SQLException ex) {
			//Handle errors for JDBC
			ex.printStackTrace();
		} catch (Exception e){
		    //Handle errors for Class.forName
			e.printStackTrace();
		}
	}
}