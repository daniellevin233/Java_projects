import java.util.Arrays;

/**
 * This class represents a library, which has a title, author, year of publication and different literary
 * aspects.
 */
class Library
{
    public static final int NEGATIVE_VALUE = -1;

    /** The maximal book capacity for this library */
    final int maximalBookCapacity;

    /** The maximal borrowed books quantity for this library */
    final int maximalBorrowedBooks;

    /** The maximal patron capacity of books for this library */
    final int maximalPatronCapacity;

    /** The current number of books holden by the library */
    int curNumOfBooks = 0;

    /** The current number of registered patrons of the library */
    int curNumOfPatrons = 0;

    /** Array of books holden by the library */
    Book[] booksArray;

    /** Array of patrons registered in the library */
    Patron[] patronsArray;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new library with the given parameters
     * @param maxBookCapacity - The maximal number of books this library can hold.
     * @param maxBorrowedBooks - The maximal number of books this library allows a single patron to borrow
     *                        at the same time.
     * @param maxPatronCapacity - The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity)
    {
        maximalBookCapacity = maxBookCapacity;
        maximalBorrowedBooks = maxBorrowedBooks;
        maximalPatronCapacity = maxPatronCapacity;
        booksArray = new Book[maxBookCapacity];
        Arrays.fill(booksArray, null);
        patronsArray = new Patron[maxPatronCapacity];
        Arrays.fill(patronsArray, null);
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Adds the given book to this library
     * @param book - The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book)
    {
        if(getBookId(book) > NEGATIVE_VALUE)
            return getBookId(book);
        if(isFullOfBooks())
            return NEGATIVE_VALUE;
        int i = 0;
        while(booksArray[i] != null)
            i++;
        booksArray[i] = book;
        curNumOfBooks++;
        return i;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId - The book id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId)
    {
        return ((bookId < maximalBookCapacity) && (bookId > NEGATIVE_VALUE) && (booksArray[bookId] != null));
    }

    /**
     * Returns true if the given number is an id of some patron in the library, false otherwise.
     * @param patronId - The patron id to check.
     * @return true if the given number is an id of some patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId)
    {
        return ((patronId < maximalPatronCapacity) && (patronId > NEGATIVE_VALUE)
                && (patronsArray[patronId] != null));
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * @param book - The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book)
    {
        return Arrays.asList(booksArray).indexOf(book);
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId - The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId)
    {
        return (isBookIdValid(bookId) && (!booksArray[bookId].isBorrowed()));
    }

    /**
     * Registers the given Patron to this library, if there is a spot available or he was a;ready registered.
     * @param patron - The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot or the patron was already
     * registered and the patron was successfully registered, a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron)
    {
        if(getPatronId(patron) > NEGATIVE_VALUE)
            return getPatronId(patron);
        if(isFullOfPatrons())
            return NEGATIVE_VALUE;
        int i = 0;
        while(patronsArray[i] != null)
            i++;
        patronsArray[i] = patron;
        curNumOfPatrons++;
        return i;
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library,
     * -1 otherwise.
     * @param patron - The patron for which finding the id number.
     * @return a non-negative id number of the given patron if he is registered to this library,
     * -1 otherwise.
     */
    int getPatronId(Patron patron)
    {
        return Arrays.asList(patronsArray).indexOf(patron);
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id,
     * if this book is available, the given patron isn't already borrowing the maximal number of books
     * allowed, and if the patron will enjoy this book.
     * @param bookId - The id number of the book to borrow.
     * @param patronId - The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId)
    {
        if(!isBookIdValid(bookId) || !isPatronIdValid(patronId) ||
                patronsArray[patronId].getNumberOfBorrowedBooks() >= maximalBorrowedBooks ||
                !patronsArray[patronId].willEnjoyBook(booksArray[bookId]) || booksArray[bookId].isBorrowed())
            return false;
        else
            booksArray[bookId].setBorrowerId(patronId);
            patronsArray[patronId].addBook(booksArray[bookId]);
            return true;
    }

    /**
     * Return the given book.
     * @param bookId - The id number of the book to return.
     */
    void returnBook(int bookId)
    {
        if(isBookIdValid(bookId))
        {
            booksArray[bookId].returnBook();
            getPatronHoldingTheBook(bookId).returnBook(booksArray[bookId]);
        }
    }

    /**
     * Find patron who is holding the book with bookId
     * @param bookId - bookId to check which one of the patrons is reading it
     * @return patron if there is one who is reading the book with bookId, null otherwise
     */
    Patron getPatronHoldingTheBook(int bookId)
    {
        if(isBookIdValid(bookId))
        {
            for(int i = 0; i < maximalPatronCapacity; i++)
            {
                if(patronsArray[i].readingBook(booksArray[bookId]))
                {
                    return patronsArray[i];
                }
            }
        }
        return null;
    }

    /**
     * Suggest the patron with the given id the book he will enjoy at most, out of all available books
     * he will enjoy, if any such exist.
     * The function finds the available book with maximal enjoy value and then if it is not enough for the
     * patron, then there is no book that will supply him.
     * @param patronId - The id number of the patron to suggest the book to.
     * @return The available book the patron with the given id will enjoy the most. Null if no book is
     * available.
     */
    Book suggestBookToPatron(int patronId)
    {
        if(isPatronIdValid(patronId) && !isEmpty())
        {
            Book maxEnjoyBook = maxEnjoyableBook();
            return (patronsArray[patronId].willEnjoyBook(maxEnjoyBook) ? maxEnjoyBook : null);
        }
        return null;
    }

    /**
     * Find the most enjoyable book by its enjoy value
     * @return book with maximal enjoy value that is available for borrowing
     */
    Book maxEnjoyableBook()
    {
        Book maxEnjoyBook = firstAvailableBook();
        for(int i = getBookId(maxEnjoyBook); i < maximalBookCapacity; i++)
        {
            if (booksArray[i] != null && isBookAvailable(i)
                    && booksArray[i].getLiteraryValue() > maxEnjoyBook.getLiteraryValue())// first we
            // check if the current book is real, then if it's available for borrowing and only
            // after that if its enjoy value is bigger
            {
                maxEnjoyBook = booksArray[i];
            }
        }
        return maxEnjoyBook;
    }

    /**
     * Find the first book that is available for borrowing
     * @return book that can be borrowed, null if such book doesn't exist
     */
    Book firstAvailableBook()
    {
        for(int i = 0; i < maximalBookCapacity; i++)
        {
            if(booksArray[i] != null && isBookAvailable(i))
            {
                return booksArray[i];
            }
        }
        return null;
    }

    /**
     * Check if the library is full of books
     * @return true if library is holding maximal books capacity, false otherwise
     */
    boolean isFullOfBooks()
    {
        return (curNumOfBooks == maximalBookCapacity);
    }

    /**
     * Check if library has any books
     * @return true if there is at least one book in the library, false otherwise
     */
    boolean isEmpty()
    {
        for(Book curBook : booksArray)
        {
            if(curBook != null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the library is full of patrons
     * @return true if library is holding maximal patrons capacity, false otherwise
     */
    boolean isFullOfPatrons()
    {
        return (curNumOfPatrons == maximalPatronCapacity);
    }
}