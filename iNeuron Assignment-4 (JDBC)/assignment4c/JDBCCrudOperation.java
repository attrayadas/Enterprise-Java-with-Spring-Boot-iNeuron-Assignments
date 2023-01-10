/* Assignment 4(c)
Perform CRUD operation using PreparedStatement
   1. insert 2. update 3. select 4. delete
 */

package assignment4c;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCCrudOperation {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws SQLException {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Scanner scanner = null;

		try {
			connection = JdbcUtil.getJdbcConnection();
			scanner = new Scanner(System.in);
			System.out.println("Enter the DB operation you want to perform:");
			System.out.println("1 - Create");
			System.out.println("2 - Read");
			System.out.println("3 - Update");
			System.out.println("4 - Delete");
			int choice = scanner.nextInt();

			if (connection != null & choice == 1) {
				System.out.println("::You have selected CREATE operation::");
				System.out.print("Enter accno: ");
				int saccno = scanner.nextInt();
				System.out.print("Enter firstname: ");
				String sfirstname = scanner.next();
				System.out.print("Enter lastname: ");
				String slastname = scanner.next();
				System.out.print("Enter bal: ");
				int sbal = scanner.nextInt();

				pstmt = connection.prepareStatement(
						"insert into account(`accno`, `lastname`, `firstname`, `bal`) values(?, ?, ?, ?)");
				pstmt.setInt(1, saccno);
				pstmt.setString(2, slastname);
				pstmt.setString(3, sfirstname);
				pstmt.setInt(4, sbal);

				int executeUpdate = pstmt.executeUpdate();
				System.out.println("Number of rows affected is: " + executeUpdate);
			}

			if (connection != null & choice == 2) {
				System.out.println("::You have selected READ operation::");
				System.out.print("Enter the account number of the customer: ");
				int saccno = scanner.nextInt();

				pstmt = connection.prepareStatement(
						"select `accno`, `lastname`, `firstname`, `bal` from account where `accno`=?");

				pstmt.setInt(1, saccno);
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) {
					System.out.println("AccNo\tLName\tFName\tBal");
					System.out.println("---------------------------");
					System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString(2) + "\t"
							+ resultSet.getString(3) + "\t" + resultSet.getInt(4));
				} else {
					System.out.println("No record found for the accno: " + saccno);
				}

			}

			if (connection != null & choice == 3) {
				System.out.println("::You have selected UPDATE operation::");
				System.out.print("Enter the Acc no. of the customer you want to update the balance: ");
				int saccno = scanner.nextInt();
				System.out.print("Enter the new balance: ");
				int sbal = scanner.nextInt();
				pstmt = connection.prepareStatement("update account set `bal`=? where `accno`=?");
				pstmt.setInt(1, sbal);
				pstmt.setInt(2, saccno);
				int executeUpdate = pstmt.executeUpdate();
				System.out.println("Number of rows affected is: " + executeUpdate);

			}

			if (connection != null & choice == 4) {
				System.out.println("::You have selected DELETE operation::");
				System.out.print("Enter the Acc no. of the customer you want to delete: ");
				int saccno = scanner.nextInt();
				pstmt = connection.prepareStatement("delete from account where `accno`=?");
				pstmt.setInt(1, saccno);
				int executeUpdate = pstmt.executeUpdate();
				System.out.println("Number of rows affected is: " + executeUpdate);
			}

			if (connection != null & (choice > 4 | choice <= 0)) {
				System.out.println("You have chosed wrong option");
			}
			System.out.println("Thank you for using this application :)");

		}

		finally {
			JdbcUtil.closeJdbcConnection(connection, pstmt, resultSet);
			if (scanner != null) {
				scanner.close();
			}
		}

	}

}
