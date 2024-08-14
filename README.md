# README OOP Project - Splitty
An expense manager for a group of friends. You can create events and manage all details and payments surrounding it.
There is also an administrator screen to manage all events (delete/back-up/import).

To start the application open your cmd in the right directory and use the following commands to start the server.
Then do the same for the client in a different window.
A password for the administrator will be printed in the cmd after starting the server.

---

## Server


Start the server while also resetting and seeding the database (recommended):

```bash
./gradlew bootrun --args='--seed'
```

Start it without modifying the database:

```bash
./gradlew bootrun
```

## Client

- Start the client with gradle:

```bash
./gradlew run
```

---

- Compile the client into a fat jar file:

```bash
./gradlew client:jar
```

- Execute the jar with java:

```bash
java -jar client/build/libs/client-0.0.1-SNAPSHOT.jar
```

Note: it will try to find the `config.properties` file in user's current directory.

## Testing

To run the automated tests:

- Start the server (required for client tests)

```bash
./gradlew bootrun --args='--seed'
```

- Run all checks and tests

```
./gradlew build
```

- To actually see the TestFX running in real time go to build.gradle (:client) and comment lines 20 to 24:

```bash
    systemProperty 'testfx.robot', 'glass'
    systemProperty 'testfx.headless', true
    systemProperty 'prism.order', 'sw'
    systemProperty 'prism.text', 't2k'
    systemProperty 'java.awt.headless', true
```

---

## Notable implemented features (for grading):
- Websockets:
  - [MyWebSocketHandler](server/src/main/java/server/component/MyWebSocketHandler.java)
  - [UpdateInterceptor](server/src/main/java/server/component/UpdateInterceptor.java)
  - [WebSocketConfig](server/src/main/java/server/component/WebSocketConfig.java)
  - [WebSocketClient](client/src/main/java/client/utils/WebSocketClient.java)
- Long-polling
  - [StatusController](server/src/main/java/server/api/StatusController.java)
  - [ServerUtils::serverOnline](client/src/main/java/client/utils/ServerUtils.java)
  - [ServerUtils::serverOnline](client/src/main/java/client/utils/ServerUtils.java)
  - [MainCtrl::initialize](client/src/main/java/client/scenes/MainCtrl.java)
- Keyboard shortcuts (Ctrl+Z)
  - Undo for everything except admin operations (creating/deleting events)
- Navigation (tab, space, enter)
  - All screens (expect Home screen) and popups can be exited with Esc
  - Mostly everything can be selected with Tab, Shift+Tab, Enter
