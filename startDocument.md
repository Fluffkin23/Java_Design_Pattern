## Name: 
-  _Husarescu Rares Matei_
-  _Costache Alin Florian_
## Student Number: 
- _4868730_
- 00000000
## Course: 
- Design patterns

## Problem Description
Imagine you are a software developer tasked with creating a simple music player application. This application will be used by people who want to listen to their favorite songs on their computer. 

The application should have the following features: 

*Song Selection*: Users should be able to browse their music library and select a song to play. They might have a large collection, so think about how to make this process user-friendly. 

*Playing Music*: Once a playlist is selected, users should be able to play the Playlist. They should also have the option to pause the song if needed. 

*Volume Control*: Users should be able to adjust the volume of the music to their liking. They might want to listen to music softly in the background or blast their favorite song, so make sure the volume control is easy to use. 

*Creating Playlists*: Users should be able to create their own playlists. They might want to make a playlist for a party, a workout, or just their favorite songs. They should be able to add songs to the playlist and remove songs from the playlist. 

*Shuffling Songs*: Sometimes, users might want to mix things up. The application should have a feature that shuffles the songs in the playlist, playing them in a random order. 

*Repeat Song*: Sometimes, users might want to listen the same song over and over again.. The application should have a feature that let the user play a selected song again and again. 

*User Interface*: All of these features should be easy to use. The application should have a simple, intuitive user interface. Users should be able to easily find and use all of the features. 

## MosCoW Analysis 

| Mo (Must have) |                                                                                                 |
|----------------|-------------------------------------------------------------------------------------------------|
|                | Music Library Management: Ability to add, remove, and manage songs in the library.              |
|                | Basic Playback Controls: Play, pause, and resume functionalities for songs.                     |
|                | Playlist Creation: Ability to create and manage playlists.                                      |
|                | Creating a song(Factory Method) : Ability to create a song by using a WAV or AIFF file                               
|                | Observer Pattern Implementation: To ensure the UI automatically updates in response to changes in the music library or playlists.                                                                                                      |
|                | Basic UI: A minimal user interface (MusicPlayerUI) that allows users to interact with the music player, including selecting songs, playing music, and viewing playlists.                                                             |
| S (Should have)|                                                                                                 |
|                | Volume Control: Ability to adjust the volume (VolumeCommand).                                   |
|                | Shuffle and Repeat: Playback strategy for playing songs in a random order and an option to repeat songs or playlists. |
| Co (Could have)|                                                                                                 |
|                | Video Playback: Support for music videos or visualizers.                                        |
| W (Won’t have) |                                                                                                 |
|                | Music Purchase Integration: Buying songs or playlists directly from the app.                    |
|                | Live Streaming Services: Integrating live music or radio streaming ser

# Design Patterns Overview

Design patterns are standard solutions to common problems in software design. Each pattern is like a blueprint that you can customize to solve a particular design problem in your code. Here's an overview of some fundamental design patterns:

## Factory Pattern (specifically Song Creation)

- **What it does:** Imagine you're at a restaurant where you can customize your meal. You choose what you want (like pasta, pizza, or a salad), but you don't need to know how the kitchen makes it. The Factory pattern works similarly for creating a song. You decide the type of song you want (like "AIFF" or "WAV"), and the Factory handles the creation details.
- **Problem it solves:** This pattern keeps you from needing to know all the technical details of how a song is made. It makes the app easier to change and grow because you can add new song types(MP3, AAC, FLAC etc.) without messing with the rest of the code.


## Strategy Pattern (specifically Playback)

- **What it does:** Think of this like having different travel routes for getting to a destination depending on the time of day or traffic. The Strategy pattern allows you to choose different ways (or strategies) for doing something, like selecting the next song to play. You can switch strategies easily, like choosing between playing songs in order, randomly, or to play the same song, only on a button press.
- **Problem it solves:** It gives  flexibility in how you do things, like playing songs, without having to change the player's core. You can easily add new ways to play the songs from a playlist as you think of them.

## Observer Pattern

- **What it does:** It's like subscribing to a newsletter. Once you sign up, you automatically get updates whenever there's news. In the Observer pattern, parts of your app "subscribe" to changes in other parts. For example, if you add a new song to a playlist, the playlist view automatically updates to show this without you having to do anything extra / or sometimes you need to press a refresh a button.
- **Problem it solves:** Keeps different parts of the app in sync without them needing to constantly check in with each other. This way, when something changes (like adding a new song, creating a new playlist etc), all subscribed parts get updated automatically, making sure the app always shows the latest info.


## MVC Architecture Section:
- **`Model`**: 
    - Represents the application's data and business logic.
    - In this application, this includes classes like Song, Playlist, PlaylistLibrary and MusicLibrary.
    - `Also, all the classes from Factory Method folder (AIFFSong, AIFFSongCreator, SongCreator, WAVSong, WAVSongCreator). For a better view/ understading we decided to keep the Factory Method folder out from the Model.`
    - The Model is responsible for managing the data, logic, and rules of the application.
- **`View`**: 
    - Represents the UI components of the application.
    - Includes classes like MusicPlayerUI, PlaylistView, and LibraryView, CreateSongView.
    - The View is responsible for displaying the data provided by the Model in a specific format.
- **`Controller`**: 
    - Acts as an interface between Model and View components.
    - Contains classes like MusicController that handle user input and convert it to commands for the Model or View.
    - The Controller interprets the input from the user, making calls to model objects to retrieve data or to view objects to display changes.

## Classes
- **`Song`** class: 
    - Represents an individual music track. 
    - Contains attributes like title, artist, and duration.
- **`Playlist`** class: 
    - Manages a collection of Song objects.
    - Allows operations like adding and removing songs, and accessing playlist details.
        - *Example*: "Workout Hits"

- **`MusicLibrary`** class: 
    - Stores and manages the entire collection of Song objects available in the application.
    - Facilitates song selection and browsing of the music library.
      
- **`MusicPlayerUI`** class: 
    - The main interface for user interaction.
    - Integrates features like playlist view and library view for user interaction.
         - *Example*: A graphical user interface with buttons for play, pause, and a slider for volume control.
           
- **`PlaylistView`** class and **`LibraryView`**: 
    - Dedicated views for displaying playlists and the music library respectively.
    - Handle user interactions specific to each view.
         - *Example*: PlaylistView shows a list of playlists, while LibraryView displays all available songs.
           
- **`MusicController`** class: 
    - Manages user interactions such as play, pause, and volume control.
    - Communicates with the model to update the state of the music player.
         - *Example*: Responds to a play button click by starting the selected song.
           
- **`Command`** (Design pattern) interface: 
    - Encapsulate different actions as executable commands.
    - Allow for flexible execution and potential undo functionalities.
         - *Example*: PlayCommand triggers the playing of a song when executed.
           
- **`PlaylistFactory`** (Design pattern) interface: 
    - Define the method for creating different types of playlists.
    - Allow for the easy addition of new playlist types.
         - *Example*:  PartyPlaylistFactory creates a playlist with upbeat, danceable tracks.
           
- **`ShuffleDecorator`** (Design pattern) class: 
    - Enhances a playlist with the capability to shuffle songs.
         - *Example*:  When applied to a playlist, the order of songs is randomized.
           
- **`SelectStrategy`** (Design pattern) interface: 
    - Define different strategies for song selection within a playlist.
         - *Example*:   RandomSelectionStrategy selects a song at random from a playlist.
           
- **`Observer`** (Design pattern) interface: 
    - Observe and respond to changes in the music library or playlists.
         - *Example*: PlaylistObserver updates the PlaylistView whenever a song is added or removed from a playlist.
           
- **`MusicPlayerApp`** class: 
    - The main class to initialize and integrate MVC components.
    - Acts as the entry point for the application.
         - *Example*:  Initializes the MusicLibrary, MusicPlayerUI, and MusicController on application startup.

## Class Diagram
![Class Diagram for Design Patterns](ClassDiagramV1.png)

### Input & Output

In this section the input and output of the application will be described
#### Input

In the table below all the input (that the user has to input in order to make the application work) are described.

| Case            | Type      | Conditions          |
|-----------------|-----------|---------------------|
| Song Selection  | Song      | not empty           |
| Play Command    | Command   | not empty           |
| Pause Command   | Command   | not empty           |
| Volume Level    | double    | 0.0 <= volume <= 1.0|
| Playlist Songs  | List<Song>| not empty           |
| Shuffle Command | Command   | not empty           |


#### Output

| Case               | Type         | Conditions                                                                 |
|--------------------|--------------|----------------------------------------------------------------------------|
| Song Selection     | String       | Display the title and artist of the selected song.                          |
| Playing Music      | Audio Output | Play the selected song; Output changes when paused/resumed.                 |
| Volume Control     | Integer      | Reflects the current volume level; Range typically from 0 (mute) to 100 (maximum).|
| Creating Playlists | List<String> | Display the list of song titles in the playlist; Updates when songs are added or removed.|
| Shuffling Songs    | List<String> | Display the shuffled order of songs in the playlist.                        |
| User Interface Feedback   | Various      | Visual and/or audio cues to confirm user actions (e.g., song selection, volume change).|

#### Calculations

| Case                       | Calculation                                   | Type   |
|----------------------------|-----------------------------------------------|--------|
| Total Duration of Playlist | Sum of the duration of all songs in the playlist | double |
| Average Song Duration      | Total duration of all songs / Number of songs  | double |
| Number of Songs in Playlist| Count of songs in the playlist                 | int    |
| Total Play Time            | Sum of the duration of all songs played        | double |