# Game Tracker Test Plan

**bold** tests remain to be implemented

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

* Basics
   * Add Game
   * Get All Games
   * Get Game
      * Get Game By Name
         * Contains None - throws IllegalArgumentException
         * Contains Exactly One - returns game
         * Contains More than One - throws IllegalArgumentExcpetion
      * Get Game By Exact Match
         * Matches Exactly - Test1, PC_Steam, 2000
   * Get Games By  - Set up list of 4 games (TG1, Other, 2000), (TG1, Steam, 2001), (TG2, Steam, 2000), (TG2, Other, 2001)
      * Name
      * Platform 
      * Year 
   * Size
      * New Set - size = 0
      * Add 2 Games - size = 2
   * isEmpty
      * New - true
      * Add 1 - false
      * Remove - true
      
## Game

* Basics
* Parse Platform
   * PC_Steam - return 
   * PC_Other - return 
   * Wii_U - return 
   * Wii - return 
   * GameCube - return 
   * Nintendo_64 - return 
   * Super_Nintendo - return 
   * DS_3DS - return 
   * DS - return 
   * GameBoy_Advance - return 
   * Playstation_3 - return 
   * Playstation_2 - return 
   * Playstation - return 
   * Unknown - return 
   * Game Thingy 1 - throws new IllegalArgumentException
* Parse Year
   * 999 - throws new IllegalArgumentException
   * 2000 - return int 2000
   * 10000 - throws new IllegalArgumentException
   * Two Thousand - throws new IllegalArgumentException

## CSVGamePersistenceManager

* Save / Load
* **Save with no data file**
* **Load with no data file**

## CSVSessionPersistenceManager

* Save / Load
* **Save with no data file**
* Load with no data file
* **Load with no game set**
