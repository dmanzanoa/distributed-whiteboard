# Distributed RMI Whiteboard

This project is a distributed shared whiteboard implemented in Java using the Remote Method Invocation (RMI) framework. It allows multiple users to draw on a shared canvas over the network while communicating with each other. The application demonstrates concurrent programming, networking and GUI development.

## Overview

The whiteboard consists of a host/server that maintains the authoritative state of the drawing and a set of clients that connect to the server to view and edit the shared canvas. Users can:

- Draw freehand lines or predefined shapes (lines, rectangles, ovals, circles).
- Select colours and line thickness.
- Erase strokes or clear the board.
- Insert text annotations.
- Participate in a simple chat for collaboration.
- Manage users (e.g. approve join requests, remove users).

The server exposes two remote interfaces:

- **RemoteInterfacePainter** – defines methods for adding shapes and synchronising the canvas state.
- **IRemoteServerFunctions** – handles user registration, messaging and host management.

The `CreateWhiteboard` class boots the RMI registry, binds the remote objects and launches the host whiteboard GUI. Clients use `JoinWhiteboard` to connect to the registry, look up the remote objects and start a local GUI.

## Repository structure

- `CreateWhiteboard.java` – entry point for the host/server.
- `JoinWhiteboard.java` – entry point for clients.
- `IRemoteServerFunctions.java` – remote interface for user management and messaging.
- `RemoteInterfacePainter.java` – remote interface for painting operations.
- `RemoteInterfacePainterImpl.java` – server-side implementation of `RemoteInterfacePainter`.
- Other classes (not included here) define shapes, user models, the whiteboard manager and GUI components.

## Usage

1. Compile the sources using `javac`:
   ```bash
   javac *.java
   ```
2. Start the server (host) from a terminal, specifying the IP address to bind, the port for the RMI registry and a username for the host:
   ```bash
   java CreateWhiteboard <hostIP> <port> <username>
   ```
3. Start one or more clients on the same or other machines:
   ```bash
   java JoinWhiteboard <serverIP> <port> <username>
   ```
4. Clients request permission from the host to join. Once accepted, they can draw on the canvas. All drawing operations and messages are synchronised across users.

## Notes

This repository includes only a subset of the original Java files to illustrate the architecture. Additional classes for shapes, GUI components and server functions would normally be part of a complete implementation. The project is intended as a portfolio example demonstrating networked GUI applications using Java RMI and concurrency.
