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
   * **Get Games By**  - Set up list of 4 games (TG1, Other, 2000), (TG1, Steam, 2001), (TG2, Steam, 2000), (TG2, Other, 2001)
      * **Name**
      * **Platform** 
      * **Year** 
   * **Size**
      * **New Set** - size = 0
      * **Add 2 Games** - size = 2
   * **isEmpty**
      * **New** - true
      * **Add 1** - false
      * **Remove** - true


## Game
