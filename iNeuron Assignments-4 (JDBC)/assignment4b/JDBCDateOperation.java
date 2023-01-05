/*
Perform Insertion Operation and also perform Retrieval operation on the following data:
   name    =>
   address =>
   gender  =>
   DOB     => dd-MM-yyyy
   DOJ     => MM-dd-yyyy
   DOM     => yyyy-MM-dd
 */

package assignment4b;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class JDBCDateOperation {

	public static void main(String[] args) throws SQLException, ParseException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);
			System.out.println("Which operation you want to perform in DB: ");
			System.out.println("1 - Insertion");
			System.out.println("2 - Retrieval");
			int choice = scanner.nextInt();

			connection = JdbcUtil.getJdbcConnection();

			if (connection != null & choice == 1) {
				System.out.println("[You have chosen INSERTION operation]");

				System.out.print("Enter Name: ");
				String sname = scanner.next();
				System.out.print("Enter Address: ");
				String saddress = scanner.next();
				System.out.print("Enter Gender (M/F): ");
				String sgender = scanner.next();
				System.out.print("Enter Date of Birth (dd-mm-yyyy): ");
				String sdob = scanner.next();
				System.out.print("Enter Date of Joining (mm-dd-yyyy): ");
				String sdoj = scanner.next();
				System.out.print("Enter Date of Marriage (yyyy-mm-dd): ");
				String sdom = scanner.next();

				// on DOB
				SimpleDateFormat sdfdob = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date utilDob = sdfdob.parse(sdob);
				long time = utilDob.getTime();
				java.sql.Date sqlDob = new java.sql.Date(time);

				// on DOJ
				SimpleDateFormat sdfdoj = new SimpleDateFormat("MM-dd-yyyy");
				java.util.Date utilDoj = sdfdoj.parse(sdoj);
				long time2 = utilDoj.getTime();
				java.sql.Date sqlDoj = new java.sql.Date(time2);

				// on DOM
				java.sql.Date sqlDom = java.sql.Date.valueOf(sdom);

				pstmt = connection.prepareStatement(
						"insert into userdetail(`name`, `address`, `gender`, `dob`, `doj`, `dom`) values(?, ?, ?, ?, ?, ?)");
				if (pstmt != null) {
					pstmt.setString(1, sname);
					pstmt.setString(2, saddress);
					pstmt.setString(3, sgender);
					pstmt.setDate(4, sqlDob);
					pstmt.setDate(5, sqlDoj);
					pstmt.setDate(6, sqlDom);
					int executeUpdate = pstmt.executeUpdate();
					System.out.println("Number of rows affected are: " + executeUpdate);
				}

			}
			if (connection != null & choice == 2) {
				System.out.println("[You have chosen RETRIEVAL operation]");
				System.out.print("Enter the name you want to get details of: ");
				String name = scanner.next();
				pstmt = connection.prepareStatement(
						"select `name`, `address`, `gender`, `dob`, `doj`, `dom` from userdetail where `name`=?");
				if (pstmt != null) {
					pstmt.setString(1, name);
					resultSet = pstmt.executeQuery();
				}
				if (resultSet != null) {
					if (resultSet.next()) {
						String sname = resultSet.getString(1);
						String saddress = resultSet.getString(2);
						String sgender = resultSet.getString(3);
						java.sql.Date sqldob = resultSet.getDate(4);
						java.sql.Date sqldoj = resultSet.getDate(5);
						java.sql.Date sqldom = resultSet.getDate(6);

						// For sqldob
						SimpleDateFormat sdfdob = new SimpleDateFormat("dd-MM-yyyy");
						String sdob = sdfdob.format(sqldob);

						// For sqldoj
						SimpleDateFormat sdfdoj = new SimpleDateFormat("MM-dd-yyyy");
						String sdoj = sdfdoj.format(sqldoj);

						// For sqldom
						SimpleDateFormat sdfdom = new SimpleDateFormat("yyyy-MM-dd");
						String sdom = sdfdom.format(sqldom);
						System.out.println("Details of " + name + " are: ");
						System.out.println("Name: " + sname);
						System.out.println("Address: " + saddress);
						System.out.println("Gender: " + sgender);
						System.out.println("Date of Birth: " + sdob);
						System.out.println("Date of Joining: " + sdoj);
						System.out.println("Date of Marriage: " + sdom);
					} else {
						System.out.println("No record found for the name: " + name);
					}

				}
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeJdbcConnection(connection, pstmt, resultSet);
			if (scanner != null) {
				scanner.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}

		}

	}

}
