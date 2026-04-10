package log_project;

import java.io.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

import javax.swing.JOptionPane;



class user_info{

    public String frst_name = "" ;
    public String last_name  = "" ;
    public String email = "" ;
    public String password = "" ;
    public int count = 0 ;

   

    public void input_set_register(HttpServletRequest req , HttpServletResponse res , user_info obj , Connection conn , PrintWriter pw){
    	
    	obj.frst_name = req.getParameter("f_name") ;
    	obj.last_name = req.getParameter("las_name") ;
    	obj.email = req.getParameter("email_reg") ;
    	
    	obj.password = req.getParameter("password_reg") ;
    	String confir_reg_password = req.getParameter("confirm_password");
    	
    	if(obj.password.equals(confir_reg_password)) {
    		querry_run_register(conn , obj , pw) ;
    	}
    	else { 
    		pw.print(false);
		}   
          
    }

    public void querry_run_register(Connection conn , user_info obj , PrintWriter pw){
    	
        try{
        
        	String querry_2 = "select email_add from log_info where email_add = ?" ;
            
            PreparedStatement preparedStatement_2 = conn.prepareStatement(querry_2) ;
            preparedStatement_2.setString(1, obj.email);
            
            
            ResultSet result = preparedStatement_2.executeQuery() ;
           
            
         
            
            if(!result.next()) {
            	
            	 String querry = "insert into log_info ( first_name , last_name , email_add , password ) values ( ? , ? , ?  , ?)" ;
                 PreparedStatement preparedStatement = conn.prepareStatement(querry) ;
                 
                 preparedStatement.setString(1, obj.frst_name);
                 preparedStatement.setString(2, obj.last_name);
                 preparedStatement.setString(3, obj.email);
                 preparedStatement.setString(4, obj.password);
                 if(obj.email.endsWith("@gamil.com")) {
                	 int temp = preparedStatement.executeUpdate();
                     if(temp > 0) {
                    	 pw.print("succesfuly register");
                     }
                     else {
    					pw.print("register fail");
    				} 
                 }
                 else {
                	 pw.print("register fail");
                 }
                 
            	
            	
            }
            else {
				String checkString = result.getString("email_add") ;
				if(checkString.equals(obj.email)) {
					pw.print("already");
				}
			}        	     
        }
        catch( Exception e ){
             
        }
        
    }

    public void querry_run_login(HttpServletRequest req , HttpServletResponse res , Connection conn , user_info obj , PrintWriter pw){
        try{
        	
        	obj.email = req.getParameter("email_log") ;
        	obj.password = req.getParameter("password_log") ;
        	
            String querry = "select email_add , password from log_info where email_add = ? and password = ? " ;
            
            PreparedStatement preparedStatement = conn.prepareStatement(querry) ;
            preparedStatement.setString(1, obj.email);
            preparedStatement.setString(2, obj.password);
            
            
            ResultSet result = preparedStatement.executeQuery() ;
          
            if(result.next()){
                pw.println("succesfully log in");
            }
            else {  
				pw.println("log in fail"); ;
			}
             
       }
        
        catch( Exception e ){  
            System.out.println(e.getMessage());
        }
    }
}


class connect_parameter{
	public static final String url = "user jdbc link" ;
	public static final String name = "root" ;
	public static final String password = "" ;
	
	public  Connection connect_database(){
	Connection conn = null ;
	    try{
	        Class.forName("com.mysql.cj.jdbc.Driver") ;
	        conn = DriverManager.getConnection( url , name , password);
	        return conn ;
	    }
	    catch( Exception e){
	        System.out.println(e.getMessage());
	    }
	    return null ;
	}
}

@WebServlet({"/log" , "/register"})

public class login_logout extends HttpServlet {
	
		public void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException , ServletException{
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			try {
				
				
				user_info obj = new user_info() ;
				
	 			connect_parameter c_paraParameter = new connect_parameter() ;
				Connection conn = c_paraParameter.connect_database() ;
				
				
				
				String path = request.getServletPath() ;
				
				if(path.equals("/log")) {
					obj.querry_run_login(request , response , conn , obj, pw) ;
				}
				else if(path.equals("/register")) {
					obj.input_set_register(request, response, obj, conn, pw);
				}
				else {
					pw.print("error") ;
				}
					
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
	}
}
	
	   
	


