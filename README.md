# GameTracker

GameTracker is a *very* simple Java stand alone program to keep track of games and when you play them. Currently it works exclusively on the command line and stores data in a local file.

I started tracking my game play on my blog several years ago and wanted a convenient way to keep track of that data. Honestly, the easiest way to do this is with a spreadsheet, but I wanted to build something that gave me a little bit more control.

This project is also a place for me to practice programming (a thing I don't do daily anymore) and to help finish projects, a thing that I've never been very good at. It's also a chance to play with all the technologies I'd like to learn.

## Libraries you need

This project uses:
- [JodaTime](https://github.com/JodaOrg/joda-time/releases) to manage dates and times,
- [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/) to manage command line arguments.

Because I'm not using any kind of build system, you'll have to retrieve and include these .jars where your JVM can find them.


## *i*FAQ 

Imaginary Frequently Asked Questions. I'm including questions that I imagine you asking me, but that you probably haven't. If you *do* have any questions, please feel free to contact me on [twitter](https://twitter.com/tjkendon).

- This all seems a bit jankey. Why is that?
  - That's because it *is* jankey. Despite having taught programming for years and built large systems for my graduate studies, I've never really put finished projects out in the world. So I'm learning and hopefully this is where I teach myself. Let me know if there's particular jank that's got your attention.
- I can't help but notice that I can't record games for systems created after 2014.
  - Not technically a question, but yeah. Sorry about that, I made an awful design decision that I'm planning to fix soon. See issue #50. 
- Why is there so much dynamic loading of things?
  - I have a problem with dynamic loading. It's the hammer I like using on all kinds of different nails. As a justification, I'll also tell you that I want to learn lots of different ways to do things, so this makes that easier.
- Can I use this to track my own game playing?
  - Knock yourself out. It certainly works, but I don't think it's going to be a super usable tool at the moment. That should improve over the next while.
- Can I use this to track anything else?
  - Yes, but you have to pretend that anything else is attached to a pre 2014 gaming system. That may get better over time. 
- Why don't you have a build system?
  - I am extremely resistant to build systems. I was teased mercilessly by `make` during my undergrad and I resent them all.

## Running and Using Game Tracker

Assuming that you are brave enough to want to use GameTracker, here's what you'll need to do.

- Download the source code from this repository. The current version of the 'main' branch is stable and *should* work.
  - Make sure you download .jar files for [JodaTime](https://github.com/JodaOrg/joda-time/releases) and [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/).
- Compile the source
- Run from the command line (making sure that JodaTime and CLI are in your classpath)

```
java GameTracker
```

There is one option

```
--datafile filename
```

Which has a short version
```
-f filename
```

both of which change the file name where the game data is saved.

- Once running, the program will present you with a menu based interface. You can choose:
  - 1 - to list all games
  - 2 - to list all play sessions
  - 3 - to list all statistics
  - A - to add a new play session including the data, the game and how long the session was
  - S - to add a new game. (You must add a game before you can use it for a session.)
  - F - to manage filters. These let you select particular period of time or games to view.
  - M - to manage data including reloading or changing the file name.
  - Q - to quit.

When the program closes, it will automatically save the to file named.

[Program](doc/media/program.png)

## Future Steps

I've completed the 2021 Refresh Project to make this marginally ready to face the world. I have a lot of inner data things I'd like to work on next, but then I think I'm going to look at making a desk-top GUI interface.

Other things I'd like to play with:
- remote storage. I've messed a lot with the google sheets API and really haven't found a solution I'm happy with there. I might also play with something nosql or something more sql based.
- web app interface.
- mobile interface.
- data exploration and visualization. This is really why were here, beyond my programmer's urge to not use the easily used tool of a spreadsheet. 

