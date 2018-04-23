# Chain of Responsibility Pattern Example

Example of using the Chain of Responsibility pattern to populate data from different sources.

A while ago, we were asked to solve a problem of populating data from different sources.  However, initially the problem was given in small pieces (SCRUM story) and we were ask to populate an object from a single source. Essentially, we wanted to populate data coming from a certain source, if it existed, but also according if some conditions were met.  Later, another source was introduced which was integrated from the existing code. A month or so later, a third source was introduced and the overall algorithm started to implement a lot of exceptions.  Just to give an idea, we started to have code such as:


```java
public UiForm getPrepopulatedUiForm(User user, Integer goalId, GoalType goalType) {

    try {
        Goal latestCompletedGoal = goalRepository.getLatestCompletedGoalForUser(user.getId());
        if (latestCompletedGoal == null || !tryToPopulateUiFormFromLatestCompletedGoal(user, newUiForm, lastestCompletedGoal)) {
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
        if (...) {
            // a lot more nested ifs here
        }

    }
}


This code is no longer readable and truly hard to maintain and extend.  What if business ask us to populate from another source? Scary!

So lets analyse what is wrong with the above code.

What went wrong?  One of the problem is when we have very small story without too much context, we have not much choice but trying to solve what it is given to us in the simplest manner.  