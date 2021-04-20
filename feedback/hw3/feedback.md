### Written Answers: 6/6

### Code Quality: 3/3

### Mechanics: 3/3

### General Feedback
- Great job!

### Specific Feedback
- When selecting a greeting in `RandomHello`, the best style would use the length of the array to specify the maximum value for the random integer generation:
```
String nextGreeting = greetings[rand.nextInt(greetings.length)];
```
Notice how this benefits us later on if we wanted to change the number of possible greetings in the array.

- Make sure to remove useless comments, like TODOs and commented out code, to make your code as clear as possible.

- Missing documentation for the new fields in `BallContainer.java` and/or `Box.java`. Make sure to document new additions in the future!
