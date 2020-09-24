daniellevin

Daniel Levin

=============================
=      File description     =
=============================

SimpleHashSet.java - abstract class representing set data structure
OpenHashSet.java - class implementing set by means of open hashing
ClosedHashSet.java - class implementing set by means of closed hashing
CollectionFacadeSet.java - class of the design facade for wrapping collections implementing SimpleSet
interface
SimpleSetPerformanceAnalyzer.java - class for analyzing performances of different set implementations
RESULTS - file containing results of performance analyzing

=============================
=          Design           =
=============================

How I implemented OpenHashSet's table:
For solving the problem of array of not-primitive objects, I've created wrapper - private nested class
representing special LinkedList. This class has the father instance as a field for rehashing purposes.

How I implemented the deletion mechanism in ClosedHashSet:
I've created additional array of booleans of the same size as array containing elements of the set, every
time that some element is deleted, the proper cell signed as true meaning that when the next search will be
made, this cell will be ignored, i.e. not recognised as free or correct for given clumped index. So thus it's
constant in terms of memory (2n instead of n), and also in terms of time, access to list cell by index costs
O(1).

=============================
=    Answers to questions   =
=============================

The results of the analysis:

Bad results for data1.txt:

Adding data1 to sets tool a lot of time for both open (27936 ms) and closed (341981 ms) sets, I suppose
that it happened because of expensive resizing operations. However, I have no idea what are the instruments
that were used by java.util packages. Even if it was possible to upgrade a bit my implementation by the
instruments we've learnt, I'm not sure that it was much more closer to java.utils performances.
Adding to closed set took more because of quadratic probing that can take some time (in particular when all
the strings in data have the same hash), not as open set that inserts elements straight in the same linked
list.


The strengths and weaknesses of each of the data structures:

All the results were much better for open set, so I can assume that open hashing is better for working with
big quantities of data, like we got in data1 and data2. Maybe for smaller tasks, closed hashing is more
efficient.


How did my two implementations compare between themselves:

As I said, the open hashing won in all operations.


How did my implementations compare to Java’s built in HashSet:

Again, as I said, my implementations were much less efficient then those of java.util 
because of some unknown reasons. (I hope we will learn more about it).
