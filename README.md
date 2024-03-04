## TicTacToe Game Design Summary
For more detailed information, refer to the full blog post: [TicTacToe Game Design Case Study](https://blog.ashwinsomnath.in/blogs/lld-case-study-2-:-design-tictactoe) 

This document outlines the design process for a TicTacToe game, covering various aspects such as requirements gathering, class diagrams, design patterns, and code implementation strategies.

#### Overview
The TicTacToe game design aims to create a full-fledged game where players can interact via a Command-Line Interface (CLI). The game supports a board of size n x n, with n-1 players and customizable symbols. The game allows for a bot player with different skill levels and offers options for selecting winning strategies. Additionally, the game features undo moves and replay functionality.

#### Class Diagrams
The class diagrams depict the structure of the game entities and their relationships. Key classes include Player, Cell, Board, Move, and Game. Various design decisions are highlighted, such as using Builder and Factory design patterns for creating the game and managing bot playing strategies.

#### Design Patterns
The design incorporates Builder, Strategy, and Factory design patterns to manage game creation, winning strategies, and bot player behavior. Builder pattern ensures the creation of games with unique player symbols, while Strategy pattern handles different bot skill levels and winning strategies.

#### Code Implementation
The code implementation involves creating project structures, defining model entities, and implementing game controllers. The GameController class manages game interactions, while the Game class handles game logic and state management. The implementation also addresses various aspects such as printing the board, making moves, and checking game states.

#### Finding the Winner
Different approaches for finding the winner are discussed, ranging from brute-force methods to optimized solutions. The document proposes an O(1) solution using maps to track winning conditions efficiently.

#### Undo Feature Implementation
Three methods for implementing the undo feature are outlined, including storing moves in a list, improving move storage with board clearing, and maintaining a list of board states. Each method has its trade-offs in terms of time complexity and memory usage.
