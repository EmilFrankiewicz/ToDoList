## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [API](#api)
* [Setup](#setup)


## General info
It's just a simple to-do list that every beginner has in their portfolio.
	
## Technologies
Spring (Boot, Data JPA, Web, Security)

## API
|                endpoint               | method | data sent with request |                  description                  |
|:-------------------------------------:|:------:|:----------------------:|:---------------------------------------------:|
| /users/register                       | POST   | UserDTO JSON           | Register user                                 |
| /api/ToDoList                         | POST   | ToDoListDTO JSON       | Create to-do list                             |
| /api/ToDoList/{listId}                | GET    |                        | Get to-do list by id                          |
| /api/ToDoList                         | GET    |                        | Get all to-do lists                           |
| /api/ToDoList/{listId}                | PUT    | ToDoListDTO JSON       | Update to-do list by id                       |
| /api/ToDoList/{listId}                | DELETE |                        | Delete to-do list by id                       |
| /api/ToDoList/{listId}/tasks          | POST   | TaskDTO JSON           | Create task of given to-do list by id         |
| /api/ToDoList/{listId}/tasks          | GET    |                        | Get all tasks from given to-do list by id     |
| /api/ToDoList/{listId}/tasks/{taskId} | GET    |                        | Get given task from given to-do list by id    |
| /api/ToDoList/{listId}/tasks/{taskId} | PUT    | TaskDTO JSON           | Update given task from given to-do list by id |
| /api/ToDoList/{listId}/tasks/{taskId} | DELETE |                        | Delete given task from given to-do list by id |

## Setup
To run this project:
```
git clone https://github.com/EmilFrankiewicz/ToDoList

create database todolist

mvn clean package

java -jar target/ToDoList-0.0.1-SNAPSHOT.jar
```
