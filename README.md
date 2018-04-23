# Chain of Responsibility Pattern Example

Example of using the Chain of Responsibility pattern to populate data from different sources.

A while ago, we were asked to solve a problem of populating data from different sources.  However, initially the problem was given in small pieces (SCRUM story) and we were ask to populate an object from a single source. Essentially, we wanted to populate data coming from a certain source, if it existed, but also according to some met conditions.  Later, another source was introduced which was integrated from the existing code. A month or so later, a third source was introduced and the overall algorithm started to implement a lot of exceptions.  Just to give an idea, we started to have code such as:


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


This code is no longer readable and truly hard to maintain and extend.  What if business ask us to populate from another source? Scary!

So lets analyse what is wrong with the above code.

What went wrong?  One of the problem is when we have very small story without too much context, we have not much choice but trying to solve what it is given to us in the simplest manner.  

So lets analyse what is wrong with the above code.
The first if statement is already smelly. The if statement contains a method call which looking at that method, is already convulated and long.

```java
...!tryToPopulateUiFormFromLatestCompletedGoal(...)

So we should avoid such coding practice.

We also have several ifs which is an indication of dealing with several conditions.
Also we can see overall that the code does not really read like a story.  The abstractions are not well define and someone from the outside will be wondering "what is the objectif here?"

Take a step back and reading the code several times, we start to understand what exactly the business want.  What the business want can be abstracted in a very simple way:
1. try to populate the data from Source A.  If that does not work,
2. try to populate the data from Source B.  If that does not work,
3. try to populate the data from Source B.  If that does not work,
4. Do not populate the object with any existing data (leave empty).

Of course, in order to populate from a certain source has several conditions to see if we should or not populate the data.  However, the above separate those concerns nicely into a `Chain of Responsibility` pattern.

So this project is a demonstration of how we can refactor the code above and implement it into a `Chain of Responsibility` by leveraging lambdas.

Here is the main snippet:

