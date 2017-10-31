package com.company.Instructor;

import com.company.Login;
import oracle.jdbc.OracleCallableStatement;
import sun.jvm.hotspot.ui.action.ShowAction;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class HomepageInstructor {
    private int input;
    private String inputString;
    private Connection connection;
    private String userId;
    private Login lastPage;
    Scanner reader = new Scanner(System.in);
    public HomepageInstructor(Connection _connection,String _userId, Login _lastPage)
    {
        show("Welcome Professor!");
        this.lastPage=_lastPage;
        this.connection=_connection;
        this.userId=_userId;
        showHomeMenu();
    }
    public void showHomeMenu()
    {
        show("Enter 1 to View/Edit Profile");
        show("Enter 2 to View/Add Courses");
        show("Enter 3 to Enroll/Drop a Student");
        show("Enter 3 to Enroll/Drop a TA");
        show("Enter 0 to go back!");
        show("Please Enter your option");
        input=getInt();
        if(input==0)
        {
            lastPage.reopen();
        }
        if(input==1)
        {
            viewProfile();
        }
        if(input==2)
        {
            viewAddCourses();
        }
        if(input==3)
        {
            enrollDropStudent();
        }
        if(input==4)
        {
            enrollDropTA();
        }
        else
        {
            showHomeMenu();
        }
    }
    public void viewProfile()
    {
        String firstName="";
        String lastName="";


        OracleCallableStatement statement;
        String sp="{call SP_VIEWPROFILE (?,?)}";
        try {
            statement = (OracleCallableStatement) connection.prepareCall(sp);
            statement.setString(1,userId.toLowerCase());
            statement.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
            statement.execute();
            ResultSet rs = statement.getCursor(2);
            if(rs!=null) {
                while (rs.next())
                {
                    firstName=rs.getString(1);
                    lastName=rs.getString(2);
                    show("First Name : " + firstName);
                    show("Last Name : " + lastName);
                    show("UserID : " + userId+"\n");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        show("Enter 1 to Edit First Name, 2 for Last Name ");
        show("Enter any other number to go back");
        input=getInt();
        if(input==0)
        {
            showHomeMenu();
        }
        if(input==1)
        {
            show("Please enter your first name:");
            inputString=getStr();

            //SP_Change profile details
            OracleCallableStatement statement2;
            String sp2="{call SP_EDITPROFILE (?,?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1,userId.toLowerCase());
                statement.setString(2,inputString);
                statement.setString(3,lastName);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if(input ==2)
        {
            show("Please enter your last name:");
            inputString=getStr();

            //SP_Change profile details
            OracleCallableStatement statement2;
            String sp2="{call SP_EDITPROFILE (?,?,?)}";
            try {
                statement2 = (OracleCallableStatement) connection.prepareCall(sp2);
                statement2.setString(1,userId.toLowerCase());
                statement2.setString(2,firstName);
                statement2.setString(3,inputString);
                statement2.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void enrollDropStudent()
    {
        show("Press 1 to enroll a student");
        show("Press 2 to drop a student");
        input=  getInt();
        if(input==1)
        {
            // Enroll a student
            show("Please enter the details of the student to enroll: UserId Firstname LastName CourseID , Ex: aadp1 bruce banner CSC540");
            inputString=getStr();
            String[] elements= inputString.split(" ");
            int i=0;
            while(elements[i].equals(""))
                i++;
            String studentID= elements[i];
            i++;
            String studentFirstName= elements[i];
            i++;
            String studentLastName= elements[i];
            i++;
            String courseID= elements[i];


            OracleCallableStatement statement;
            String sp="{call SP_ENROLLSTUDENT(?,?,?,?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1,userId.toLowerCase());
                statement.setString(2,studentID);
                statement.setString(3,studentFirstName);
                statement.setString(4,studentLastName);
                statement.setString(5,courseID);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if(input==2)
        {
            // Drop a student
            show("Please enter the details of the student to drop: UserId CourseID , Ex: aadp1 CSC540");
            inputString=getStr();
            String[] elements= inputString.split(" ");
            int i=0;
            while(elements[i].equals(""))
                i++;
            String studentID= elements[i];
            i++;
            while(elements[i].equals(""))
                i++;
            String courseID= elements[i];


            OracleCallableStatement statement;
            String sp="{call SP_DROPSTUDENT(?,?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1,userId.toLowerCase());
                statement.setString(2,studentID);
                statement.setString(3,courseID);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showHomeMenu();
        }
    }


    public void enrollDropTA()
    {
        show("Press 1 to enroll a TA");
        show("Press 2 to drop a TA");
        input=  getInt();
        if(input==1)
        {
            // Enroll a student
            show("Please enter the details of the TA to enroll: UserId  CourseID , Ex: aadp1 CSC540");
            inputString=getStr();
            String[] elements= inputString.split(" ");
            int i=0;
            while(elements[i].equals(""))
                i++;
            String TAID= elements[i];
            i++;
            String courseID= elements[i];


            OracleCallableStatement statement;
            String sp="{call SP_ENROLLTA(?,?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1,userId.toLowerCase());
                statement.setString(2,TAID);
                statement.setString(3,courseID);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if(input==2)
        {
            // Drop a student
            show("Please enter the details of the TA to drop: UserId CourseID , Ex: aadp1 CSC540");
            inputString=getStr();
            String[] elements= inputString.split(" ");
            int i=0;
            while(elements[i].equals(""))
                i++;
            String TAID= elements[i];
            i++;
            while(elements[i].equals(""))
                i++;
            String courseID= elements[i];


            OracleCallableStatement statement;
            String sp="{call SP_DROPTA(?,?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1,userId.toLowerCase());
                statement.setString(2,TAID);
                statement.setString(3,courseID);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showHomeMenu();
        }
    }


    public void viewAddCourses()
    {
        show("Press 1 to View exsiting courses, and 2 to add a new Course .... Press 0 to go back");
        input=getInt();
        if(input==1) {

            OracleCallableStatement statement;
            String sp="{call SP_VIEWPROFCOURSES (?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1,userId.toLowerCase());
                statement.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
                statement.execute();
                ResultSet rs = statement.getCursor(2);
                if(rs!=null) {
                    while (rs.next())
                    {
                        show("CourseID : " + rs.getString(1));
                        show("Course Name : " + rs.getString(2));
                        show("Start Date : " + rs.getString(3));
                        show("End Date : " + rs.getString(4));
                        show("Professor Name : " + rs.getString(7));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(input==2)
        {
            // Add  Course
            show("Please enter the details of the course to drop: ");
            show("Enter CourseID");
            String courseID=getStr();
            show("Enter CourseName");
            String courseName=getStr();
            show("Enter StartDate");
            String startDate=getStr();
            show("Enter EndDate");
            String endDate=getStr();


            OracleCallableStatement statement;
            String sp="{call SP_ADDCOURSE(?,?,?,?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1,userId.toLowerCase());
                statement.setString(2,courseID);
                statement.setString(3,courseName);
                statement.setString(4,startDate);
                statement.setString(5,endDate);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if(input ==0)
        {
            showHomeMenu();
        }
    }

    public void show(String statement)
    {
        System.out.println(statement);
    }
    public String getStr()
    {
        // Reading from System.in
        String str = reader.nextLine();
        if(str.equals(""))
            str = reader.nextLine(); // Scans the next token of the input as an int.
        return str;
    }

    public void reopen()
    {
        this.showHomeMenu();
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
}
