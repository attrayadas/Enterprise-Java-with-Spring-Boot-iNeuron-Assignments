/* Assignment 4(a)
Give the menu to the user as the operation listed below on student table:
   1. Create 2. Read 3. Update 4. Delete
 */

package assignment4a;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCCrudOperation {

	public static void main(String[] args) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Scanner sc = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice", "root", "root1234");

			sc = new Scanner(System.in);
			System.out.println("Enter the DB operation you want to perform:");
			System.out.println("1 - Create");
			System.out.println("2 - Read");
			System.out.println("3 - Update");
			System.out.println("4 - Delete");
			int choice = sc.nextInt();

			if (connection != null) {
				statement = connection.createStatement();

				if (choice == 1 & statement != null) {
					System.out.println("[You have selected INSERT operation]");
					System.out.println("Enter accno: ");
					int saccno = sc.nextInt();
					System.out.println("Enter firstname: ");
					String sfirstname = sc.next();
					System.out.println("Enter lastname: ");
					String slastname = sc.next();
					System.out.println("Enter bal: ");
					int sbal = sc.nextInt();
					String sqlQuery = String.format(
							"insert into account(`accno`, `lastname`, `firstname`, `bal`) values(%d,'%s', '%s', %d)",
							saccno, slastname, sfirstname, sbal);
					int executeUpdate = statement.executeUpdate(sqlQuery);
					System.out.println("Number of rows affected=" + executeUpdate);

				}

				if (choice == 2 & statement != null) {
					System.out.println("[You have selected READ operation]");
					resultSet = statement.executeQuery("select accno, lastname, firstname, bal from account");

					if (resultSet != null) {
						System.out.println("================================");
						System.out.println("AccNo FirstName LastName Balance");
						System.out.println("================================");
						while (resultSet.next()) {
							int accno = resultSet.getInt("accno");
							String lastname = resultSet.getString("lastname");
							String firstname = resultSet.getString("firstname");
							int bal = resultSet.getInt("bal");
							System.out.println(accno + "\t" + firstname + "\t" + lastname + "\t" + bal);
						}
						System.out.println("================================");
					}
				}

				if (choice == 3 & statement != null) {
					System.out.println("[You have selected UPDATE operation]");
					System.out.println("Enter accno of customer of which you want to change the balance");
					int accno = sc.nextInt();
					System.out.println("Enter the new Balance of the customer");
					int bal = sc.nextInt();
					String sqlQuery = String.format("update account set bal=%d where accno=%d", bal, accno);
					int executeUpdate = statement.executeUpdate(sqlQuery);
					System.out.println("Number of rows affected=" + executeUpdate);
				}

				if (choice == 4 & statement != null) {
					System.out.println("[You have selected DELETE operation]");
					System.out.println("Enter accno of customer you want to delete");
					int accno = sc.nextInt();
					String sqlQuery = String.format("delete from account where accno=%d", accno);
					int executeUpdate = statement.executeUpdate(sqlQuery);
					System.out.println("Number of rows affected=" + executeUpdate);
				}
				if (choice <= 0 | choice > 4) {
					System.out.println("Wrong input, please try again!");
				}
				System.out.println("Thanks for using the JDBC Aplication :)");
			}
		} catch (SQLException s) {
			s.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
			if (sc != null) {
				sc.close();
			}

		}

	}

}
