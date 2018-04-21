# Game Tracker Test Plan

**bold** tests remain to be implemented

## Play Data

* Add Session
* Get Sessions

## Play Session

* **Basics**
* **Parse Play Time**
   * **Correct "1.1" - return double**
   * **Incorrect "One Point One" - throws IllegalArgumentException**
* **Parse Date Time**
   * **Empty - return DateTime."now"**
   * **Correct "2000/12/31" - matching datetime**
   * **Incorrect "Today" - throws IllegalArgumentException**
   
## GameSet

* **Basics**
   * **Add Game**
   * **Get All Games**
   * **Get Game**
      * **Get Game By Name**
         * **Contains None - throws IllegalArgumentException**
         * **Contains Exactly One - returns game**
         * **Contains More than One - throws IllegalArgumentExcpetion**
      * **Get Game By Exact Match**
         * **Matches Exactly - Test1, PC_Steam, 2000**



## Game
