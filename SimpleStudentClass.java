package p10_package;


/**
 * Binary Search Tree node class for managing
 * student data
 * 
 * @author MichaelL
 *
 */
public class SimpleStudentClass
   {
    public String name;
           
    public int studentID;
           
    public char gender;
     
    public double gpa;

    /**
     * Initialization constructor for data
     * <p>
     * Note: Class does not require a default constructor
     * 
     * @param inName name of student to be input into object
     * 
     * @param inStudentID ID number of student to be input into object
     * 
     * @param inGender gender of student to be input into object
     * 
     * @param inGPA gpa of student to be input into object
     * 
     */
    public SimpleStudentClass( String inName, 
                           int inStudentID, char inGender, double inGPA )
       {
        name = inName;
         
        studentID = inStudentID;
         
        gender = inGender;
         
        gpa = inGPA;
       }
    
    /**
     * Copy constructor
     * 
     * @param copied SimpleStudentClass object to be copied
     * <p>
     * Note: Class does not require a default constructor
     */
    public SimpleStudentClass( SimpleStudentClass copied )
       {
        name = copied.name;
       
        studentID = copied.studentID;
        
        gender = copied.gender;
        
        gpa = copied.gpa;
       }

    @Override
    public String toString()
       {
        return name + '/' + studentID + '/' + gender + '/' + gpa;           
       }      
   }
