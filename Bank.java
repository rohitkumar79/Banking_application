import java.awt.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.sql.*;
import javax.swing.*;
public class Bank extends JFrame implements ActionListener
{
static JLabel label1,label2,label3,sep;
static JButton sign,create,deposit,withdraw,trans,balance,close,logout;
static JTextField txtA1;
static JTextField txtA2;
static Connection con;
static Statement st1,st2,st3;
static ResultSet rs1;
int numd;         //to be deposited
int numf;                //actual amount in account(balance)
int nume;    //to be withdrawn
public Bank()
   {
 	super(" MY Bank ");
 	setLayout(new FlowLayout());
 	try	{
 		 
 	 	 con = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Rohit/Desktop/Java Project/bank.mdb");
 	 	 st1=con.createStatement();
		 rs1=st1.executeQuery("select * from tab1");
		}
	catch(Exception e)
		{
		 System.out.println("there was some error in establishing connection : "+e);
		}
	 sep = new JLabel("Existing User Signin  :  ");
	add(sep);
	 label1 = new JLabel(" Account Number : ");
	add(label1);
         txtA1 = new JTextField(15);
	add(txtA1);
	 label2 = new JLabel("        Name :       ");
	add(label2);
	 txtA2 = new JTextField(15);
	add(txtA2);
	 sign=new JButton("SIGN IN");
	add(sign);
	

         sep = new JLabel("                                                                                           ");
	add(sep);
        

         //sep = new JLabel("                               ");
	//add(sep);
	

	 sep = new JLabel("New User Signup  :  ");
	add(sep);
         create=new JButton("CREATE NEW ACCOUNT");
	add(create);
	 sep = new JLabel("                                                                                          ");
	add(sep);
	 sep = new JLabel("                                                    ");
	add(sep);
	 deposit=new JButton("DEPOSIT");
	add(deposit);
	 withdraw=new JButton("WITHDRAW");
	add(withdraw);
	 trans=new JButton("TRANSACTION");
	add(trans);
         balance=new JButton("BALANCE");
	add(balance);
	 logout=new JButton("LOGOUT");
	add(logout);
	 sep = new JLabel("                                                                                      ");
	add(sep);
	 close=new JButton("CLOSE");
	add(close);
	 create.addActionListener(this);
	 deposit.addActionListener(this);
	 withdraw.addActionListener(this);
	 trans.addActionListener(this);
	 balance.addActionListener(this);
	 logout.addActionListener(this);
	 close.addActionListener(this);
         sign.addActionListener(this);
         
	 deposit.setEnabled(false);
	 withdraw.setEnabled(false);
	 trans.setEnabled(false);
	 balance.setEnabled(false);
  }
 public void actionPerformed(ActionEvent e)
	 {
if(e.getSource()==create)
	 	 {
	 	 	String stra = JOptionPane.showInputDialog(null,"Please enter the NAME to open a NEW ACCOUNT");
	 	 	String strb = JOptionPane.showInputDialog(null,"Please enter the ACCOUNT NUMBER");
	 	 if(stra==null || strb==null)
			{
			   JOptionPane.showMessageDialog(null," Either any one or both the values were left blank");
	           deposit.setEnabled(false);
			   withdraw.setEnabled(false);
			   trans.setEnabled(false);
			   balance.setEnabled(false);
			   create.setEnabled(true);
		       sign.setEnabled(true);	 
			}
		else
		 {
	 	 	try
		    {
			st2=con.createStatement();       //st2 is a statement object
			String rr="insert into tab1 values('"+stra+"','"+strb+"',0,0,0)";   //url for values into the database
			st2.executeUpdate(rr);
			st2.close();                                                           //close the statement object
			JOptionPane.showMessageDialog(null,"ACCOUNT Created Successfully");	
			txtA1.setText(strb);                         //once created successfully, keep the acc no and name on the text fields for ease of login
			txtA2.setText(stra);
		   }
		catch(Exception e2)
			 {
			 	JOptionPane.showMessageDialog(null," There is already a user with same ACCOUNT NUMBER ,Give another ACCOUNT NUMBER ");
			 }
		   	deposit.setEnabled(false);
			withdraw.setEnabled(false);
			trans.setEnabled(false);
			balance.setEnabled(false);
			create.setEnabled(false);
		    sign.setEnabled(true);	
		}
	 }
else if(e.getSource()==sign)
	 	 {
	 	  	 	String num=txtA1.getText();     //get the number value from the acc number string
	 	 	try
				{
					st1.close();				
					st1=con.createStatement();
					rs1=st1.executeQuery("Select * from tab1 where AccNum Like '"+num+"'");    //search for the acount number in the table
					rs1.next();
					txtA1.setText(rs1.getString("AccNum"));
					txtA2.setText(rs1.getString("UserName"));
				}
			catch(Exception e2)
				{
					JOptionPane.showMessageDialog(null,"Invalid Account Number");
				}				
			deposit.setEnabled(true);
			withdraw.setEnabled(true);
			trans.setEnabled(true);
			balance.setEnabled(true);
			create.setEnabled(false);
	 	 }
else if(e.getSource()==deposit)
	      	 {
	      	 	String strc = JOptionPane.showInputDialog(null,"Please enter the amount to be DEPOSITED"); //open a new window to take user input
	 	 	    numd=Integer.parseInt(strc);
	 	 	    int numb=Integer.parseInt(txtA1.getText());	
	 	 	    	if (numd<=0)
	 	 	    	{
	 	 	    		JOptionPane.showMessageDialog(null," NO Amount Deposited");
	 	 	    	}
	 	 	    	else
	 	 	    	{
   	 	 	     try
				{
			    int num,num1;
			    st3=con.createStatement();
		    	rs1=st3.executeQuery("select * from tab1 where Accnum like '"+txtA1.getText()+"'");
		    	rs1.next();
		    	num1=Integer.parseInt(rs1.getString("Deposit"));   //take in the values from the database
		    	num=Integer.parseInt(rs1.getString("Balance"));		//take in value from the database 
		    	JOptionPane.showMessageDialog(null,"Amount Deposited Successfully , Amount is "+numd);
		    	numf=num+numd;       //new balance
		    	num1=num1+numd;        //to show total deposited
				st2=con.createStatement();
				String ss="update tab1 set Deposit="+num1+" where AccNum="+numb+"";    //url to set new deposit amount
			        String aa="update tab1 set Balance="+numf+" where AccNum="+numb+"";    //url to set new balance
				st2.executeUpdate(ss);
				st2.executeUpdate(aa);
				st2.close();
				st3.close();
				}
			catch(Exception e2)
				{
			JOptionPane.showMessageDialog(null,"Amount can not be DEPOSITED");
				}
	 	     }
	      	 }
else if(e.getSource()==withdraw)
	   {
	        String strd = JOptionPane.showInputDialog(null,"Please enter the amount to be WITHDRAWN");  //show dialog box to withdraw amount
	        nume=Integer.parseInt(strd);
	        int numc=Integer.parseInt(txtA1.getText());
	        if(numf<=nume)                               //if amount withdrawn is > amount in account then error
			{
			   JOptionPane.showMessageDialog(null,"Sorry can not Withdraw, no sufficient Balance");
			}
			else
			{
	 	 	try
		    {
		    	int num1;
			st3=con.createStatement();
		    	rs1=st3.executeQuery("select * from tab1 where Accnum like '"+txtA1.getText()+"'");  //get data of that acc numbr in result set
		    	rs1.next();
		    	num1=Integer.parseInt(rs1.getString("Withdraw"));
		    	numf=Integer.parseInt(rs1.getString("Balance"));
		    	JOptionPane.showMessageDialog(null,"Amount Withdrawn is "+nume);
		    	numf=numf-nume;		//update balance in account 
		    	num1=num1+nume;		//total withdrawn	
		    	st2=con.createStatement();
			    String pp="update tab1 set Withdraw="+num1+" where AccNum="+numc+"";
			    String pp1="update tab1 set Balance="+numf+" where AccNum="+numc+"";
				st2.executeUpdate(pp);
				st2.executeUpdate(pp1);
				st2.close();
		    }
		catch(Exception e2)
			 {
			 	JOptionPane.showMessageDialog(null," Can't WITHDRAW ");
			 }	
		    }
	   }
		
else if(e.getSource()==balance)
	 	 {
             String num=txtA1.getText();	 	 	
	 	 	
	 	 	try
		    {
		       	
				st3=con.createStatement();
		    	rs1=st3.executeQuery("select * from tab1 where Accnum like '"+num+"'");
		    	rs1.next();
		    	numf=Integer.parseInt(rs1.getString("Balance"));    //get balance data from the table
		    	
				JOptionPane.showMessageDialog(null,"Balance is "+numf);	
		    }
		catch(Exception e2)
			 {
			 	 	JOptionPane.showMessageDialog(null," Balance null ");
			 }
	     
	 }
else if(e.getSource()==trans)
	 	 {	
	 	 String strq = JOptionPane.showInputDialog(null,"Enter the ACCOUNT NUMBER to view the TRANSACTION DONE");
	 	 int numk=Integer.parseInt(strq);    //get the acc number from the user to view trannsactions
	         int numa=Integer.parseInt(txtA1.getText());
	 		if(numk==numa)
	 		{
	 		JOptionPane.showMessageDialog(null,"Amount Deposited  is "+numd);
	 		JOptionPane.showMessageDialog(null,"Amount Withdrawn is "+nume);
	 		}
	 	 else
	 	 {
	 	 	JOptionPane.showMessageDialog(null,"The Account Number is not the same as in the TEXTFIELD");
	 	 }
	 	 }
	 	 
else if(e.getSource()==logout)
	 	 {
	 	 	txtA1.setText("");   //clear acc num test field
			txtA2.setText("");    //clear name text field
			deposit.setEnabled(false);       //visibility off of all buttons
			withdraw.setEnabled(false);
			trans.setEnabled(false);
			balance.setEnabled(false);
			create.setEnabled(true);
            sign.setEnabled(true);
	 	 }
else if(e.getSource()==close)
	 	 {
	 	System.exit(0);
	 	 }
	  }
 public static void main(String args[])
   {
   	Bank obj = new Bank();
   	obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	obj.setSize(250,500);
   	obj.setVisible(true);  
    }

}