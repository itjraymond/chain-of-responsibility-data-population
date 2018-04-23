# Chain of Responsibility Pattern Example

Example of using the Chain of Responsibility pattern to populate data from different sources.

A while ago, we were asked to solve a problem of populating data from different sources.  However, initially the problem was given in small pieces (SCRUM story) and we were ask to populate an object from an external source and if that didn't work, populate from our database. Essentially, we wanted to pre-populate data coming from various sources.  For each source of data, certain conditions were also required. Later, another external source of data was introduced which was integrated from the existing code. A month or so later, additional conditions were added and the overall algorithm started to implement a lot of exceptions with lots of if..else statements.  Just to give an idea, we started to have code such as:


```java
public UiForm getPrepopulatedUiForm(User user, Integer goalId, GoalType goalType) {

    try {
        Goal latestCompletedGoal = goalRepository.getLatestCompletedGoalForUser(user.getId());
        if (latestCompletedGoal == null || !tryToPopulateUiFormFromLatestCompletedGoal(user, 
                        newUiForm, lastestCompletedGoal)) {
            populateDataFromInFlightGoal(newUiForm, user);
        }
        if (...) {
            // another long method name call
        } else {
            // log this situation
        }
        if (...) {
            // yet another long method name call
        }
        if (!goalType.equals(GoalType.EDUCATION) &&
            !newUiForm.getRegulatoryQuestions().getQuestions()
            .streams()
            .anyMatch( question -> question.getQuestionId().equals("q5"))) {

            // more code here
        }

    } catch (Exception e){
        ...
    }
    // more supporting code with other supporting classes

    // Total lines: ~ 500 lines
}
```

This code is not readable and truly hard to maintain and extend.  What if business ask us to populate from yet another source? Scary!

What went wrong?  One of the problem is when we have very small story without too much context, we have not much choice but trying to solve what it is given to us in the simplest manner.  

So lets point out few major flaws with the above code.
The first if statement is already smelly. The if statement contains a method call which looking at that method, is already convulated and long. The name of the method kind of let us know what we are trying to do but also seems to say return a boolean to tell us success if it was successful or not.

```java
...!tryToPopulateUiFormFromLatestCompletedGoal(...)
```
So we should avoid such coding practice for if statements.

We also have several ifs which is an indication of dealing with several conditions. But all the if statements are no too clear making it hard to figure out how does this data gets populated.

Furthermore, we can overall see, that the code does not really read like a story.  The abstractions are not well define and someone from the outside will be wondering "what exactly are we trying to do?" "Where is the population of data coming from?" (and many more questions)

Taking a step back and reading the code several times, we start to understand what exactly the business were trying to achieve.  What the business want can be abstracted in a very simple way:

 1. try to populate the data from Source A.  If that does not work,
 2. try to populate the data from Source B.  If that does not work,
 3. try to populate the data from Source C.  If that does not work,
 4. Do not populate the object with any existing data (leave empty).

Of course, each population of data from the different source have their own conditions but their concerns are now separated. The main concerns are nicely separated into a `Chain of Responsibility` pattern: "try this, if that does not work, try that and if that does not work, try something else and so forth".

So this project is a demonstration of how we can refactor the code above and implement it into a `Chain of Responsibility` by leveraging `lambdas` from Java 8.

# The main snippet:

```java
public Optional<UiForm> populate(User user) {
    return Stream.<Function<User, Optional<UiForm>>>of(
            PrePopulationServiceImpl::populateFromSourceA,
            PrePopulationServiceImpl::populateFromSourceB,
            PrePopulationServiceImpl::populateFromSourceC,
            PrePopulationServiceImpl::populateFromNothing)
            .map(fn -> fn.apply(user))
            .filter(Optional::isPresent)
            .findFirst()
            .flatMap(Function.identity());
}

private static Optional<UiForm> populateFromSourceA(User user) {...}
private static Optional<UiForm> populateFromSourceB(User user) {...}
private static Optional<UiForm> populateFromSourceC(User user) {...}
```

# Explanation:
We first construct a stream of functions.  The order of those function is important because it will be the order they will be called.  The .map is responsible to call the function which will return an Optional.  The .filter is responsible to verify if the executed function has returned an Optional with a result.  The .findFirst will simply stop the sequence as soon as we have obtained an Optional with a result. The .flatMap only makes our returned result into the proper type.

Essentially, this code will start to execute each function in the stream and as soon as one of the function is successful, it stop there and the functions down the stream are not executed.  In other word, we try the first function, if that does not work, we try the second one, if that does not work, we try the third one and so forth.

The code read well because we can see that we are trying to populate from Source A, then Source B, then Source C and last we do not populate.
We can also easily change the order or add more sources. We can also isolate any excpetions within the proper source methods.

Now it is much simpler to maintain and extend and also much more readable for any new commers.