package p10_package;

/**
 * @author Scott Ames
 */
public class LinkedHashClass
{
    /**
     * local node class for holding student data and next reference link

     * @author Scott Ames
     *
     */
    private class LinkedNodeClass
    {
        private SimpleStudentClass data;
        
        private LinkedNodeClass nextRef;
        
        private LinkedNodeClass( SimpleStudentClass inData )
        {
            data = inData;
            
            nextRef = null;
        }
    }
    
    /**
     * Table size default
     * <p>
     * Table size (capacity) is recommended to be a prime number
     */
    private final int DEFAULT_TABLE_SIZE = 11;
    
    /**
     * Size of the base table
     */
    private int tableSize;
    
    /**
     * Constant used to control access operation
     */
    private final boolean REMOVE = true;
    
    /**
     * Constant used to control access operation
     */
    private final boolean SEARCH = false;
    
    /**
     * Array for hash table
     */
    private LinkedNodeClass[] tableArray;
    
    /**
     * Default constructor
     * <p>
     * Initializes array to default table size
     */
    public LinkedHashClass()
    {
        tableSize = DEFAULT_TABLE_SIZE;
        
        tableArray = new LinkedNodeClass[ tableSize ];
        
        int index;
        
        for( index = 0; index < tableSize; index ++ )
        {
            tableArray[ index ] = null;
        }
    }
    
    /**
     * Initialization constructor
     * <p>
     * initializes array to specified table size
     * 
     * @param inTableSize - sets table size
     */
    public LinkedHashClass( int inTableSize )
    {
        tableSize = inTableSize;
        
        tableArray = new LinkedNodeClass[ tableSize ];
        
        int index;
        
        for( index = 0; index < tableSize; index ++ )
        {
            tableArray[ index ] = null;
        }
    }
    
    /**
     * copy constructor
     * 
     * @param copied - linkedHashClass object to be copied
     */
    public LinkedHashClass( LinkedHashClass copied )
    {
        int index;
        LinkedNodeClass lclWkgRef, cpdWkgRef;

        tableSize = copied.tableSize;

        tableArray = new LinkedNodeClass[ tableSize ];

        for( index = 0; index < tableSize; index++ )
        {
            cpdWkgRef = copied.tableArray[ index ];

            if( cpdWkgRef != null )
            {
                lclWkgRef = new LinkedNodeClass( cpdWkgRef.data );
                
                tableArray[ index ] = lclWkgRef;
     
                cpdWkgRef = cpdWkgRef.nextRef;

                while( cpdWkgRef != null )
                {
                    lclWkgRef.nextRef = new LinkedNodeClass( cpdWkgRef.data ); 

                    cpdWkgRef = cpdWkgRef.nextRef;

                    lclWkgRef = lclWkgRef.nextRef;
                }
            }

            else
            {
                tableArray[ index ] = null;
            }

        }
    }
    
    /**
     * Helper method that handles both searching and removing items 
     * in linked list at specified index
     * 
     * @param linkIndex - integer index specifying location in array
     * 
     * @param studentID - integer key for searching and/or removing node
     * 
     * @param removeFlag - boolean flag that indicates whether to search 
     * or remove use SEARCH, REMOVE constants to call this method)
     * 
     * @return SimpleStudentClass data found and/or removed
     */
    private SimpleStudentClass
                        accessLinkedData( int linkIndex,
                                        int studentID, boolean removeFlag )
    {
        SimpleStudentClass tempVal = null;
        LinkedNodeClass workingRef = tableArray[ linkIndex ];
        
        if( workingRef != null )
        {
            if( workingRef.data.studentID == studentID )
            {
                tempVal = workingRef.data;
                
                if( removeFlag )
                {
                    tableArray[ linkIndex ] = workingRef.nextRef;
                }
            }
            
            else
            {
                while( workingRef.nextRef != null 
                        && workingRef.nextRef.data.studentID != studentID )
                {
                    workingRef = workingRef.nextRef;
                }
                
                if( workingRef.nextRef != null )
                {
                    tempVal = workingRef.nextRef.data;
                }
                
                if( removeFlag )
                {
                    workingRef.nextRef = workingRef.nextRef.nextRef;
                }
            }
        }
        
        return tempVal;
    }

    /**
     * Adds item to hash table
     * <p>
     * Uses overloaded addItem with object
     * 
     * @param inName - name of student
     * 
     * @param inStudentID - student ID
     *  
     * @param inGender - gender of student
     * 
     * @param inGPA - gpa of student
     * 
     * @return Boolean success of operation
     */
    public boolean addItem( String inName, int inStudentID,
                            char inGender, double inGPA )
    {
        SimpleStudentClass itemToAdd = new SimpleStudentClass( 
                        inName, inStudentID, inGender, inGPA );
        
        return addItem( itemToAdd );
    }

    /**
     * Adds item to hash table
     * <p>
     * Overloaded method that accepts SimpleStudentClass object
     * 
     * @param newItem - student class object
     * 
     * @return boolean success of operation
     */
    private boolean addItem( SimpleStudentClass newItem )
    {      
        int hashIndex = generateHash( newItem );
        
        appendToList( hashIndex, newItem );
        
        if( findItem( newItem.studentID ) != null )
        {
            return true;
        }
        
        return false;
    }
    /**
     * Appends new data to end of list at given list index; 
     * handles empty node at that index as needed
     * 
     * @param listIndex - specified integer of array
     * 
     * @param newNode - SimpleStudentClass node to be appended to array/list
     */
    public void appendToList( int listIndex, SimpleStudentClass newNode )
    {
        LinkedNodeClass currentRef;
        currentRef = tableArray[ listIndex ];

        if(  currentRef == null )
        {
            tableArray[ listIndex ] = new LinkedNodeClass( newNode );
            
            return;
        }
        
        while( currentRef.nextRef != null )
        {
            currentRef = currentRef.nextRef;
        }
        
        tableArray[ listIndex ].nextRef = new LinkedNodeClass( newNode );
    }
    
    /**
     * Clears hash table by clearing all linked list elements
     */
    public void clearHashTable()
    {
        int index;
        
        for( index = 0; index < tableSize; index ++ )
        {
            tableArray[ index ] = null;
        }
    }
    /**
     * Method recursively counts number of nodes in a given linked list
     * 
     * @param workingRef - LinkedNodeClass reference used for recursion;
     * initially set to head
     * 
     * @return SimpleStudentClass object removed, or null if not found
     */
    private int countNodes( LinkedNodeClass workingRef )
    {
        
        if( workingRef == null )
        {
            return 0;
        }

        return (1 + countNodes( workingRef.nextRef ) );
    }
    
    /**
     * Searches for item in hash table using student ID as key
     * 
     * @param studentID - used for requesting data
     * 
     * @return SimpleStudentClass object found, or null if not found
     */
    public SimpleStudentClass findItem( int studentID )
    {
        int itemsIndex = generateHash( new SimpleStudentClass( 
                "", studentID, 'X', 0.00 ) );
        
        return accessLinkedData( itemsIndex, studentID, SEARCH );
    }
    
    /**
     * Method uses student ID to generate hash value for 
     * use as index in hash table
     * <p>
     * Process sums the Unicode values of the student ID 
     * numbers converted to characters
     * 
     * @param studentData - SimpleStudentClass object from which 
     * hash value will be generated
     * 
     * @return integer hash value to be used as array index
     */
    public int generateHash( SimpleStudentClass studentData )
    {
        int totalSum = 0, studentID, currentInt;
        studentID = studentData.studentID; 
        
        while( studentID > 0 )
        {
            currentInt = studentID % 10;
            
            totalSum += (int) ( currentInt + '0' );
            
            studentID /= 10;
        }
        
        return totalSum % tableSize;
    }
    
    /**
     * Removes item from hash table, using student ID as key
     * 
     * @param studentID - used for requesting data
     * 
     * @return SimpleStudentClass object removed, or null if not found
     */
    public SimpleStudentClass removeItem( int studentID )
    {
        int itemsIndex = generateHash( new SimpleStudentClass( 
                "", studentID, 'X', 0.00 ) );
        
        return accessLinkedData( itemsIndex, studentID, REMOVE );
    }
    
    /**
     * traverses through all array bins, finds lengths of 
     * each linked list, then displays as table
     * <p>
     * Shows table of list lengths, then shows table size, then shows the
     *  number of empty bins and the longest linked list of the set; 
     *  adapts to any size array
     */
    public void showHashTableStatus()
    {
        int index;
        String linkedOutput, indexOutput, dashes;

        System.out.println( "Array node report:" );
        
        indexOutput = ( "\tIndex:\t" );
        dashes = ( "\t      \t" );
        linkedOutput = ( "\t      \t" );
        
        for( index = 0; index < tableSize; index++ )
        {
           indexOutput += index + "\t"; 
           dashes += "-----\t";
           linkedOutput += countNodes( tableArray[ index ] ) + "\t";
        }
        
        System.out.println( indexOutput );
        System.out.println( dashes );
        System.out.println( linkedOutput );
        
        System.out.println( "With a table size of  " + tableSize );
        System.out.println( "The number of empty bins was " );
    }
}