import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class movie_data_search {
	
	
	public static void create_relation_data_insert(Connection conn, Statement stmt) {
		try {
			//1�ܰ� ���̺� �����ϱ�
			stmt.executeUpdate("drop table movie");
			stmt.executeUpdate("create table movie (    id           char(3),     title        varchar (100),     company      varchar (50),    releasedate  date,    country      varchar (10),      totalscreen  int,    profit       numeric (15,2),    totalnum     int,    grade        varchar (50),    primary key (id));");

			//2�ܰ� ���� �о insert �ϱ�.
			//���� ��ü ����
	        File file = new File("movie_data.txt");
	        //�Է� ��Ʈ�� ����
	        FileReader filereader = new FileReader(file);
	        //�Է� ���� ����
	        BufferedReader bufReader = new BufferedReader(filereader);
	        String line = "";  
	        while((line = bufReader.readLine()) != null){
	        	String[] array = line.split("\\|");
	        	String insert_data = "INSERT INTO movie VALUES ('"+ array[1] +"', '"+ array[2] +"', '"+array[3]+"', '"+array[4]+"', '"+array[5]+"', " +array[6] + ", " + array[7] + ", " + array[8] + ", '" + array[9]+ "');";
	            stmt.executeUpdate(insert_data);
	            //System.out.println(line);
	        }
	        System.out.println("creating relation and inserting data are successful!!");
		}catch (SQLException ex) {
			//Handle errors for JDBC
			ex.printStackTrace();
		} catch (Exception e){
		    //Handle errors for Class.forName
			e.printStackTrace();
		}
	}
	
	public static void find_by_title(Connection conn, Statement stmt, ResultSet rs) {
		try {
			System.out.print("����� �Է� : ");
			Scanner sc = new Scanner(System.in);
			String title = sc.nextLine();
			System.out.println(title);
			String selectsql = "select * from movie where title like '%"+ title+"%' order by id";
			rs = stmt.executeQuery(selectsql);
			int count =0;
			System.out.println("\n-------�˻����------");
        	System.out.println(" id | title | company | releasedate | country | totalscreen | profit | totalnum | grade" );
            while(rs.next()) {
            	count++;

            	System.out.println(rs.getString(1)+ " | " + rs.getString(2)+ " | "+ rs.getString(3)+ " | "+ rs.getDate(4)+ " | "+ rs.getString(5)+ " | "+ rs.getLong(6)+ " | " + rs.getLong(7)+ " | " + rs.getLong(8)+ " | " + rs.getString(9));
            }
		}catch (SQLException ex) {
			//Handle errors for JDBC
			ex.printStackTrace();
		} catch (Exception e){
		    //Handle errors for Class.forName
			e.printStackTrace();
		}
	}
	public static void find_by_totalnum(Connection conn, Statement stmt, ResultSet rs) {
		try {
			System.out.println("�Է��� ���� �ʰ��ϴ� �������� ���� ��ȭ�� �˻��մϴ�.");
			System.out.print("����� �Է� : ");
			Scanner sc = new Scanner(System.in);
			int totalnum = sc.nextInt();
			String selectsql = "select * from movie where totalnum  >"+ totalnum+" order by totalnum;";
			rs = stmt.executeQuery(selectsql);
			int count =0;
			System.out.println("\n-------�˻����------");
        	System.out.println(" id | title | company | releasedate | country | totalscreen | profit | totalnum | grade" );
            while(rs.next()) {
            	count++;

            	System.out.println(rs.getString(1)+ " | " + rs.getString(2)+ " | "+ rs.getString(3)+ " | "+ rs.getDate(4)+ " | "+ rs.getString(5)+ " | "+ rs.getLong(6)+ " | " + rs.getLong(7)+ " | " + rs.getLong(8)+ " | " + rs.getString(9));
            }
		}catch (SQLException ex) {
			//Handle errors for JDBC
			ex.printStackTrace();
		} catch (Exception e){
		    //Handle errors for Class.forName
			e.printStackTrace();
		}
	}
	public static void find_by_date(Connection conn, Statement stmt, ResultSet rs) {
		try {
			
			System.out.println("�Ʒ� ���� ����");
			System.out.println("ex) : 2013-01-10, 2013-01-15");
			System.out.print("����� �Է� (, �� �����Ͽ� �Է��Ұ�): ");
			Scanner sc = new Scanner(System.in);
			String dates = sc.nextLine();
			String[] date_arr = dates.split(",");
			
			String selectsql = "select * from movie where '" + date_arr[0].trim() + "' <= releasedate and releasedate <= '" + date_arr[1].trim() + "'  order by releasedate;";
			rs = stmt.executeQuery(selectsql);
			int count =0;
			
			System.out.println("\n-------�˻����------");
        	System.out.println(" id | title | company | releasedate | country | totalscreen | profit | totalnum | grade" );
            while(rs.next()) {
            	count++;

            	System.out.println(rs.getString(1)+ " | " + rs.getString(2)+ " | "+ rs.getString(3)+ " | "+ rs.getDate(4)+ " | "+ rs.getString(5)+ " | "+ rs.getLong(6)+ " | " + rs.getLong(7)+ " | " + rs.getLong(8)+ " | " + rs.getString(9));
            }
		}catch (SQLException ex) {
			//Handle errors for JDBC
			ex.printStackTrace();
		} catch (Exception e){
		    //Handle errors for Class.forName
			e.printStackTrace();
		}
	}
	
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
			stmt = conn.createStatement();
			
            
            String menu = "========================================\n(0) ����\n(1) �����̼� ���� �� ������ ����\n(2) ������ �̿��� �˻�\n(3) �������� �̿��� �˻�\n(4) �������� �̿��� �˻�\n========================================\n���ϴ� ��ȣ�� �Է� �Ͻÿ� : ";
            Scanner sc = new Scanner(System.in);
            
            boolean end_flag = false;
            while(true) {
            	System.out.print(menu);
            	int command = sc.nextInt();
            	switch(command) {
            		case 0:
            			end_flag = true;
            			System.out.println("���α׷��� ����˴ϴ�.");
            			break;
            		case 1:
            			create_relation_data_insert(conn, stmt);            			
            			break;
            		case 2:
            			find_by_title(conn, stmt, rs);
            			break;
            		case 3:
            			find_by_totalnum(conn, stmt, rs);
            			break;
            		case 4:
            			find_by_date(conn, stmt, rs);
            			break;
            		default:
            			System.out.println("�� �Է� �ٶ��ϴ�.");
            	}
            	if(end_flag == true)
            		break;
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
