package com.company;

import com.company.Instructor.HomepageInstructor;
import com.company.Student.HomepageStudent;
import com.company.TA.HomepageTA;
import oracle.jdbc.OracleCallableStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class Login {
    private String username;
    private String password;
    private String role;
    String input;
    private Connection connection;
    Scanner reader = new Scanner(System.in);
    public Login(Connection _connection)
    {
        this.connection=_connection;
        showLoginPage();
        reader.close();
    }
    public void showLoginPage()
    {
        show("Please enter login credentials:");
        show("Username:");
        username=getStr();
        show("Passwords:");
        password=getPassword();
        show("Are you a Professor? Y or N:");
        input=getStr();

        if(input.equals("Y"))
        {
            role="P";
        }
        else
        {
            show("Are you a TA? Y or N:");
            input=getStr();
            if(input.equals("Y"))
            {
                role="T";
            }
            else
            {
                role="S";
            }
        }
        show("Enter 1 to login, 0 to retry:");
        int option = getInt();
        if(option==1)
        {
            OracleCallableStatement statement;
            String sp="{call sp_login (?,?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1,username);
                statement.setString(2,password);
                statement.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = statement.getCursor(3);
                String found="0";
                if(rs!=null) {
                    while (rs.next()) {
                        found = rs.getString(1);
                    }
                    show(found);
                    if (found.equals("1")) {
                        if (role.equals("P")) {
                            clearScreen();
                            HomepageInstructor homepage = new HomepageInstructor(connection, username, this);
                        } else {
                            if (role.equals("T")) {
                                clearScreen();
                                HomepageTA homepage = new HomepageTA(connection, username, this);
                            } else {
                                clearScreen();
                                HomepageStudent homepage = new HomepageStudent(connection, username, this);
                            }
                        }
                    }
                }
                /*else {
                    ResultSet rs = statement.getResultSet();
                    while (rs.next()) {
                        System.out.println("Name : " + rs.getString(3));
                    }
                }*/
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showLoginPage();
        }
    }
    public void show(String statement)
    {
        System.out.println(statement);
    }
    public String getStr()
    {
          // Reading from System.in
        String str = reader.nextLine(); // Scans the next token of the input as an int.
        return str;
    }

    public String getPassword() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            password = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return password;
    }


    public int getInt()
    { // Reading from System.in
        int input = reader.nextInt(); // Scans the next token of the input as an int.
        return input;
    }

    public final  void clearScreen()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
    public void reopen()
    {
        this.showLoginPage();
    }
}
