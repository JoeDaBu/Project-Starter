# My Personal Project

## Alarm Clock

- My Alarm Clock will function as any other alarm clock but with added functionality.
- Anyone watching an alarm on their computer can use it.
- I chose this project because I was annoyed with my alarm clock.
- Also, because of the pay wall in alarm apps.

##Phase 1 User Stories
- As a user, I want to be able to add an alarm to my alarm list
- As a user, I want to be able view the list of active alarms
- As a user, I want to be able remove alarms from my alarm list
- As a user, I want to be able to sort the list of alarms

##Phase 2 User Stories
- As a user, I want to be able to save my AlarmList  to file
- As a user, I want to be able to load my AlarmList from file
- As a user, I want to be able to sort Alarms alphabetically
- As a user, I want to be able to quit from the application window, I want any changes to be saved without having to be prompted to
- As a user, when I start the application I want to be given the option to load my AlarmList

##Phase 3 User Stories
- As a user, I want to be able to add multiple alarms to my gui
- As a user, I want to be able to load and save the state of the GUI application
- As a user, I want my alarms to write a message when they do off
- As a user, I want to be able to sort alarms by time they will go off

##Phase 4 Task2
- Tested and designed the and DaysList as a robust class. 4 methods throw a checked exception. sortDays(), addDay(daysOfTheWeek day), removeDay(DaysOfTheWeek day), changeDay(DaysOfTheWeek changeDay, DaysOfTheWeek newDay), all throw checked exceptions.
- Type Hierarchy in GUI: AlarmClock, AlarmControllerPanelLabels, LabelsWithImage, LabelsWithoutImage, are all in a type hierarchy under the Observer class.
- Bi-directional associations, between: Update and AlarmClock, Update and AlarmController, Update and MenuBar, MenuBar and AlarmController.
  
### For the Future
- As a user, I want to be able select the audio type to be played when the alarm to go off
- As a user, I want to be able select the puzzle to complete in order to stop the alarm
- As a user, I want to be able to track my alarm schedule
- As a user, I want to be able to sort my alarm and settings list by clicking and holding
- As a user, I want to add a settingsList, and a savedsettings classes

##Citations:
The Console App took heavy influence from the CPSC 210 Teller App
Found at: https://github.students.cs.ubc.ca/CPSC210/TellerApp

The implementation of saving and loading data took heavy influence from the CPSC 210 JsonDemo
Found at: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

Icon Image is from:
https://society6.com/product/geometric-cool-elephant_wall-clock

The implementation of the alarm sound is taken directly from:
CPSC 210/ALarmSystem

The alarm sound file is taken from:
https://soundcloud.com/mirefixtape/when-the-shitpost-levels-too-high

