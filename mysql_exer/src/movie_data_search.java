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
			//1단계 테이블 생성하기
			stmt.executeUpdate("drop table movie");
			stmt.executeUpdate("create table movie (    id           char(3),     title        varchar (100),     company      varchar (50),    releasedate  date,    country      varchar (10),      totalscreen  int,    profit       numeric (15,2),    totalnum     int,    grade        varchar (50),    primary key (id));");

			//2단계 파일 읽어서 insert 하기.
			//파일 객체 생성
	        File file = new File("movie_data.txt");
	        //입력 스트림 생성
	        FileReader filereader = new FileReader(file);
	        //입력 버퍼 생성
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
			System.out.print("사용자 입력 : ");
			Scanner sc = new Scanner(System.in);
			String title = sc.nextLine();
			System.out.println(title);
			String selectsql = "select * from movie where title like '%"+ title+"%' order by id";
			rs = stmt.executeQuery(selectsql);
			int count =0;
			System.out.println("\n-------검색결과------");
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
			System.out.println("입력한 값을 초과하는 관객수를 가진 영화를 검색합니다.");
			System.out.print("사용자 입력 : ");
			Scanner sc = new Scanner(System.in);
			int totalnum = sc.nextInt();
			String selectsql = "select * from movie where totalnum  >"+ totalnum+" order by totalnum;";
			rs = stmt.executeQuery(selectsql);
			int count =0;
			System.out.println("\n-------검색결과------");
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
			
			System.out.println("아래 예시 참고");
			System.out.println("ex) : 2013-01-10, 2013-01-15");
			System.out.print("사용자 입력 (, 로 구분하여 입력할것): ");
			Scanner sc = new Scanner(System.in);
			String dates = sc.nextLine();
			String[] date_arr = dates.split(",");
			
			String selectsql = "select * from movie where '" + date_arr[0].trim() + "' <= releasedate and releasedate <= '" + date_arr[1].trim() + "'  order by releasedate;";
			rs = stmt.executeQuery(selectsql);
			int count =0;
			
			System.out.println("\n-------검색결과------");
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
			
            
            String menu = "========================================\n(0) 종료\n(1) 릴레이션 생성 및 데이터 삽입\n(2) 제목을 이용한 검색\n(3) 관객수를 이용한 검색\n(4) 개봉일을 이용한 검색\n========================================\n원하는 번호를 입력 하시오 : ";
            Scanner sc = new Scanner(System.in);
            
            boolean end_flag = false;
            while(true) {
            	System.out.print(menu);
            	int command = sc.nextInt();
            	switch(command) {
            		case 0:
            			end_flag = true;
            			System.out.println("프로그램이 종료됩니다.");
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
            			System.out.println("재 입력 바랍니다.");
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
