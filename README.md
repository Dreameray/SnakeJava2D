# Snake Game - Java 2D Implementation

## Introduction

A classic arcade game recreated using Java Swing and AWT libraries. This project showcases fundamental game development concepts including collision detection, game loops, keyboard input handling, and different game states.

## Game Features

- **Main Menu**: Choose between two different game modes.
- **Classic Mode**: The traditional snake game experience where eating food grows your snake.
- **Power-Up Mode**: Adds an exciting twist with different food types:
  - **Blue Food (Speed)**: Doubles your snake's speed, making navigation more challenging.
  - **Magenta Food (Triple Growth)**: Adds three segments at once and resets speed to normal.
  - **Yellow Food (Reduction)**: Removes one segment from your snake.
- **Score Tracking**: Current score visible during gameplay.
- **High Score System**: Your best score is saved and displayed.
- **Game Over Screen**: Shows final score with options to restart or return to menu.

## How to Play

1. **Starting the Game**:
   - At the main menu, press `1` to play Classic Mode
   - Press `2` to play Power-Up Mode

2. **Controls**:
   - Use arrow keys to control the snake's direction
   - Press `Space` to restart after game over
   - Press `Escape` to return to the main menu after game over

3. **Objective**:
   - Classic Mode: Eat food to grow your snake and achieve the highest score possible
   - Power-Up Mode: Same as classic, but strategically use power-ups to your advantage

4. **Game Over Conditions**:
   - Colliding with the snake's own body
   - Hitting the boundaries of the play area

## Technical Implementation

This game demonstrates several key Java programming concepts:
- Object-oriented design with classes for game objects
- Event handling for keyboard input
- Game state management
- Rendering graphics with Java 2D
- Timer-based animation
- Random number generation for food placement
