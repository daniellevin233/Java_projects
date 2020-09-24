daniellevin

Daniel Levin

=============================
=      File description     =
=============================

filesprocessing package - general package containing CommandParser, DirectoryProcessor and Section classes in
direct way, and three sub-packages:

filesprocessingexceptions package - package containing general exceptions dealing with filesprocessing direct
classes

filters package - package containing all the classes related to filters

orders package - package containing all the classes related to orders

=============================
=          Design           =
=============================

    =====================================
    =      filesprocessing package      =
    =====================================

    DirectoryProcessor - facade design, manages all the processes in the program
    CommandParser - parser for file with commands
    Section class - represents one ready section with it filter, order and warnings

    =============================
    =      filters package      =
    =============================

    Each filter implements Filter interface, with method accept. There is an hierarchy between the filters in
    accordance to its functionality, usual inheritance. Interface itself has default method filter that
    performs filtering.

    FilterException - empty exception having a symbolic meaning for type 1 errors related to filter

    =============================
    =      orders package      =
    =============================

    Each order implements Order interface, with method order. There is an hierarchy between the filters in
    accordance to its functionality, usual inheritance.

    OrderException - empty exception having a symbolic meaning for type 1 errors related to order

    ===============================================
    =      filesprocessingexceptions package      =
    ===============================================

    ErrorException - exception for type 2 errors

    SectionErrorException, SubSectionLackErrorException - two exceptions inheriting ErrorException. Both of
    them has more specific meaning, and its unique error message.

    WarningException - exception for type 1 errors, constructed with line, in which the warning occured

=============================
=    Answers to questions   =
=============================

Sorting: I have sorted matched files by quick sort, and I used array list for this purposes, to keep their
order and efficient sorting helper methods