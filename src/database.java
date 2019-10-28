import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Scanner; 

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class database implements ActionListener{
public static Connection conn1;
public static JTextArea ta;
public static JScrollPane scroll;
public database (String name){      
		final JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 500);
        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("EDIT");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem( new AbstractAction("Drop Tables") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                dropTables();
            }
        });
        JMenuItem m22 = new JMenuItem( new AbstractAction("Create Tables") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                createTables();
            }
        });
        JMenuItem m33 = new JMenuItem( new AbstractAction("Populate Tables") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                populateTables();
            }
        });
        JMenuItem m55 = new JMenuItem( new AbstractAction("Query Tables") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                queryTables();
            }
        });
        JMenuItem m44 = new JMenuItem( new AbstractAction("Exit") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                try {
					conn1.close();
					System.out.println("connection closed.");
					frame.dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        m2.add(m11);
        m2.add(m22);
        m2.add(m33);
        m2.add(m55);
        m1.add(m44);
        
        ta = new JTextArea();
        scroll = new JScrollPane(ta);
        
        
        JPanel panel = new JPanel(); // the panel is not visible in output
        

        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, scroll);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.setVisible(true);
		
        
	}
	public void queryTables(){
			String query = "select firstname, employeeID from employee";
			ta.append(query + "\n");
			try (Statement stmt = conn1.createStatement()) {

			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String firstname = rs.getString(1);
				int employeeid = rs.getInt(2);
				ta.append(firstname + ", " + employeeid + "\n");
			}
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}
			query = "SELECT SALARY FROM EMPLOYEE, DOCTOR WHERE DOCTOR.EMPLOYEEID = EMPLOYEE.EMPLOYEEID";
			ta.append(query + "\n");
			try (Statement stmt = conn1.createStatement()) {

			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int salary = rs.getInt(1);
				ta.append(salary+"\n");
			}
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}
			query = "select * from PRESCRIPTIONS";
			ta.append(query + "\n");
			try (Statement stmt = conn1.createStatement()) {

			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int drugid = rs.getInt(1);
				String drugname = rs.getString(2);
				String dosage = rs.getString(3);
				ta.append(drugid+", " +  drugname + ", " + dosage + "\n");
			}
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}
			query = "select * from employee";
			ta.append(query + "\n");
			try (Statement stmt = conn1.createStatement()) {

			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				String[] values = new String[columnsNumber];
				for(int i = 0; i < columnsNumber; i++){
					values[i] = rs.getString(i+1);
					ta.append(values[i] + ", ");
				}
				ta.append("\n");
			}
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}
	}
	public void createTables(){
		File file = new File("create_tables.sql"); 
	    Scanner sc;
		try {
			sc = new Scanner(file);
			sc.useDelimiter(";"); 
			String command;
		    while(sc.hasNext()){
		    command = sc.next();
		    ta.append(command+"\n");

			try (Statement stmt = conn1.createStatement()) {
			
			stmt.execute(command);
			ta.append("works\n");
			
			} catch (SQLException e) {
				System.out.println(e.getErrorCode()+" error");
			}
			}
		    sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error: drop_tables.sql not found");
			e.printStackTrace();
		}	
	} 
	public void dropTables(){
        	File file = new File("drop_tables.sql"); 
    	    Scanner sc;
    		try {
    			sc = new Scanner(file);
    			sc.useDelimiter(";"); 
    			String command;
    		    while(sc.hasNext()){
    		    command = sc.next();
    		    ta.append(command+"\n");
   
    			try (Statement stmt = conn1.createStatement()) {
    			
    			stmt.execute(command);
    			ta.append("works\n");
    			
    			} catch (SQLException e) {
    				System.out.println(e.getErrorCode()+" error");
    			}
    			}
    		    sc.close();
 
    		} catch (FileNotFoundException e) {
    			System.out.println("Error: drop_tables.sql not found");
    			e.printStackTrace();
    		}	
      }    
	
	public void populateTables(){
		File file = new File("populate_tables.sql"); 
	    Scanner sc;
		try {
			sc = new Scanner(file);
			sc.useDelimiter(";"); 
			String command;
		    while(sc.hasNext()){
		    command = sc.next();
		    ta.append(command+"\n");

			try (Statement stmt = conn1.createStatement()) {
			
			stmt.execute(command);
			ta.append("works\n");
			
			} catch (SQLException e) {
				System.out.println(e.getErrorCode()+" error");
			}
			}
		    sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error: drop_tables.sql not found");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
	conn1 = null; 
	try {
	            // registers Oracle JDBC driver - though this is no longer required
	            // since JDBC 4.0, but added here for backward compatibility
	            Class.forName("oracle.jdbc.OracleDriver");
	 
	           
	         //   String dbURL1 = "jdbc:oracle:thin:username/password@oracle.scs.ryerson.ca:1521:orcl";  // that is school Oracle database and you can only use it in the labs
																							
	         	
	             String dbURL1 = "jdbc:oracle:thin:nbarss/cps510@oracle.scs.ryerson.ca:1521:orcl";
				/* This XE or local database that you installed on your laptop. 1521 is the default port for database, change according to what you used during installation. 
				xe is the sid, change according to what you setup during installation. */
				
				conn1 = DriverManager.getConnection(dbURL1);
	            if (conn1 != null) {
	                System.out.println("Connected with connection #1");
	            }
	            
				
			
	            //In your database, you should have a table created already with at least 1 row of data. In this select query example, table testjdbc was already created with at least 2 rows of data with columns NAME and NUM.
				//When you enter your data into the table, please make sure to commit your insertions to ensure your table has the correct data. So the commands that you need to type in Sqldeveloper are
				
	
	            database myData = new database("Medical Database");
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			} catch (SQLException ex) {
				ex.printStackTrace();
			} 
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
