### Written Answers: 18/20

# Representation Exposure
## Iterator Mutation
For #5, note that `Iterator` contains a `remove` method which can mutate the
underlying data.

## Passing in a Mutable Object
For #6, note that a list is mutable, and that if `cards` is stored in our
representation the client could modify `cards` after initialization.

### Design: 2/3

# Design
## Pathfinding
Your graph ADT should not implement any pathfinding. Clients should handle pathfinding themselves
if they want it.

## Multiple Top Level classes
Are you sure you want to have multiple top level classes? In general, it's bad style
to have a single abstraction spread across multiple top level classes.

### Documentation & Specification (including JavaDoc): 3/3

### Testing (test suite quality & implementation): 3/3

### Code quality (code stubs/skeletons only, nothing else): 3/3

### Mechanics: 3/3

#### Overall Feedback
Good job in this homework! Please address the design feedback to improve!

#### More Details

None.
