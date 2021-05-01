### Written Answers: 23/26

# `checkRep`
## Immutability Guaranteed By The Compiler
Final immutable fields cannot be modified after they are instantiated; the
compiler would complain about any attempt to do so.  Therefore, we can reason
that `RatNum` and `RatTerm` cannot contain any bugs with regard to the
representation invariant as long as we ensure the coherency of the data at
initialization.  Therefore, `RatNum` and `RatTerm` are special cases that do not
need calls to `checkRep` at the beginning and end of every public method, aside
from the constructor.

## Immutable ADT Needs `checkRep`
Immutability is a property of the specification, and `checkRep` does not assume
the specification was correctly implemented.  So, in general, regardless of
whether or not they are immutable, ADTs need calls to `checkRep` at the
beginning and end of all public methods.

### Code Quality: 3/3

### Mechanics: 3/3

### General Feedback
Good job in this homework! Please address the Feedback to improve!

### Specific Feedback

## Missing Calls to checkRep in RatPoly and/or RatPolyStack
Missing calls to `checkRep` at the beginning and end of every public method in `RatPoly` and `RatPolyStack`.

Missing inline comment on complicated code such as sortedInsert and div.
