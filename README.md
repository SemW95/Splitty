# OOPP Project

## Server

Start the server while also resetting and seeding the database:

```bash
./gradlew bootrun --args='--seed'
```

Start it without modifying the database:

```bash
./gradlew bootrun
```

## Client

Start the client:

```bash
./gradlew run
```

## Testing

To test everything the Gitlab pipeline does:

- Start the server (required for client tests)

```bash
./gradlew bootrun --args='--seed'
```

- Run all checks and tests

```
./gradlew build
```
