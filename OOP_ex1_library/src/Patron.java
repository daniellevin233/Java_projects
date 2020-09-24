import java.util.Vector;

/**
 * This class represents a patron, which has a title, author, year of publication and different literary
 * aspects.
 */
public class Patron {

    /** The first name of the patron. */
    final String firstName;

    /** The last name of the patron. */
    final String lastName;

    /** The weight the patron assigns to the comic aspects of books. */
    final int comicValue;

    /** The weight the patron assigns to the dramatic aspects of books. */
    final int dramaticValue;

    /** The weight the patron assigns to the educational aspects of books. */
    final int educationalValue;

    /** The minimal literary value a book must have for this patron to enjoy it. */
    final int enjoymentThreshold;

    /** Vector of borrowed books at the current moment */
    Vector<Book> borrowedBooks;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new patron with the given characteristic.
     * @param patronFirstName The first name of the patron.
     * @param patronLastName The last name of the patron.
     * @param comicTendency The weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency The weight the patron assigns to the dramatic aspects of books.
     * @param educationalTendency The weight the patron assigns to the educational aspects of books.
     * @param patronEnjoymentThreshold The minimal literary value a book must have for this patron to enjoy
     *                                it.
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold)
    {
        firstName = patronFirstName;
        lastName = patronLastName;
        comicValue = comicTendency;
        dramaticValue = dramaticTendency;
        educationalValue = educationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;
        borrowedBooks = new Vector<Book>();
    }

    /*----=  Instance Methods  =-----*/

    /**
     * @return the String representation of this patron.
     */
    String stringRepresentation()
    {
        return firstName + " " + lastName;
    }

    /**
     * @param book - Book object to check if patron will enjoy it
     * @return true if this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book)
    {
        return (getBookScore(book) >= enjoymentThreshold);
    }

    /**
     * Add book to the patron's borrowings list
     * @param book - book to add to the patron's borrowings list
     */
    void addBook(Book book)
    {
        borrowedBooks.add(book);
    }

    /**
     * Remove book from the patron's borrowings list
     * @param book - book to remove from list
     */
    void returnBook(Book book)
    {
        borrowedBooks.remove(book);
    }

    /**
     * Check if the patron is reading book at the current moment, i.e. if it is in his borrowings list
     * @param book - book to check
     * @return true if book is in borrowings list of the patron, false otherwise
     */
    boolean readingBook(Book book)
    {
        return borrowedBooks.contains(book);
    }

    /*----=  Getters  =-----*/

    /**
     * @param book - book whose score will be counted
     * @return the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book)
    {
        return ((book.getComicValue() * comicValue) + (book.getDramaticValue() * dramaticValue) +
                (book.getEducationalValue() * educationalValue));
    }

    /**
     * @return current number of borrowed books
     */
    int getNumberOfBorrowedBooks()
    {
        return borrowedBooks.size();
    }
}
