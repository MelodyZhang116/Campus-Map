### Command line application: 3/3

- Your command line app requires the user to hit enter after the path is printed in order to get another prompt. This is not intuitive to use.

- Nice handling of invalid file names

### Design: 2/3

#### `MarvelPaths` As An ADT
You might have already noticed this, but implementing `MarvelPaths` as an ADT
makes it awkward to implement, and awkward to use in the `MarvelTestDriver`.  It
turns out you can reuse most of the interface defined in your graph ADT if you
keep `MarvelPaths` as simply a collection of `static` methods that operate on
graphs passed in as arguments.
- Even if the user wants to find multiple paths in the same graph, your implementation recreates the graph each time from the csv file which is an unnecessary time suck.

### Documentation & Specification (including JavaDoc): 3/3

- Your findPaths javadoc should specify how ties are broken. It should also specify the behavior if no path is found.

### Code quality (code and internal comments including RI/AF when appropriate): 3/3

- Your code in parseData is indented way too far.

- Good job adding internal comments

### Testing (test suite quality & implementation): 1/3

#### Special Cases
Your test suite is lacking in coverage.  Here's a few ideas for interesting test
cases:
- Empty data file.
- Loading two graphs.
- Operations that mix graph loading and building.
- Cyclic graphs with path finding.
- Lexicographically least paths.

### Mechanics: 3/3

### Command line application extra credit:  -/3

#### Overall Feedback

Please see my notes on MarvelPaths as an ADT and why it's the wrong design decision. Good luck on hw7!

#### More Details

None.
