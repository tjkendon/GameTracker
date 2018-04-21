# Game Tracker Test Plan

## Play Data

* Add Session
* Get Sessions

## Play Session

* Basics
* Parse Play Time
   * Correct "1.1" - return double
   * Incorrect "One Point One" - throws IllegalArgumentException
* Parse Date Time
   * Empty - return DateTime."now" 
   * Correct "2000/12/31" - matching datetime
   * Incorrect "Today" - throws IllegalArgumentException
   
## GameSet


## Game
