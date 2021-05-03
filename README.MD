# Java CLI project

This project is part of my university academic program.
It implements simple CLI, that controls some data collections (and useful utils) via a network.

This project contains 2 parts: `server` and `client`.

## Installation

### Client
```
cd client
mvn install
java -jar target/client-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Server
```
cd server
mvn install
java -jar target/server-1.0-SNAPSHOT-jar-with-dependencies.jar
```


## Commands

```
help - Returns all avail commands (with short description)
info - Returns information about current storage (creation time, elements count, storage type and etc.)
clear - Cleans storage
insert - Adds new StudyGroup to storage by provied key
show - Returns information about stored Objects in current storage
update - Updates storage StudyGroup by provied id
sum_of_students_count - Returns sum of all study groups students
filter_less_than_students_count - Shows study groups with student count lower, than provided key (long)
min_by_id - Show study group with lowest id
remove_greater - Remove all StudyGroups where students count greater, than in StudyGroup provided by key
remove_lower - Remove all StudyGroups where students count lower, than in StudyGroup provided by key
remove_greater_key - Remove all StudyGroups where students count greater, that value (long)
remove_key - Removes StudyGroup from storage by provied key
exit - Close program (without save in file)
execute_script - Execute commands from file
```

### Server-only commands

```
save - Save storage to dump.xml
```

### Client-only commands

```
exit - Close program (without save in file)
```

## Usage

This is a small example of interaction with the program

```
execute_script examples/script
show
info
```

## Meta
```
Lab 5 variant: 12009 
Proof: https://docs.google.com/spreadsheets/d/1mKBuYxcuHVE2R1ZudkjkWBBd8l4liftFLo1GhpxH46Q

Lab 6 variant: 127761
Proof: https://docs.google.com/spreadsheets/d/1ZqBMwWWox4KCZeMbkbbuR_HfQ-kupdB_Lf3BR15zFiU
```
