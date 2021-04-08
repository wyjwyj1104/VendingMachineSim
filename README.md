# vintri_backend

## Introduction

`vintri_backend` 3rd party API to create an express.js base API.
* Note: All error handling are placed with error code 500 internal server error for consistency.
* Start date: 202103182300
* V0: 202103181112 Project setup.
* V1: 202103190116 Endpoint tested.


## Source
Beer lists reference Punk API:
* https://punkapi.com/documentation/v2

Regex Tester:
* https://www.regextester.com/


## Prerequisite

* [NodeJs 12](https://nodejs.org/en/) It is recommended to use a version manager such as [NVM](https://github.com/nvm-sh/nvm) to install NodeJS.

## Setup and running the app

```
$ npm install
$ npm start
```

## .env
* Since it is a public project I decided to just put the password here just in case who wants to view the DB.

```
NODE_ENV=dev
DB_URL=mongodb+srv://cluster1.zksz5.mongodb.net/vintri
DB_USER=admin
DB_PASS=54y4oH9VVGMbJRxL
```

## Test

### Unit test

```
$ npm test
```

### Postman  test

* **Run Server:**
```
$ npm start
```

* **Basic Auth:** Username: "admin", Password: "qwe123"
* **Get beer by name:** GET http://localhost:3000/beers?name=Bad
* **Get all beers list:** GET http://localhost:3000/beers/all
* **Add beer rating by ID:** PUT http://localhost:3000/beers/6054564a15ad51a92e9b4c5c?rating=1

## Fundamentals

The code is divided into different modules which consist of 3 layers: Controller, Business Service, and Data Model.

* **Controllers:** are responsible for handling incoming request and returning response to the client.
* **Services:** are responsible for handling the actual business logic.
* **Models:** are the representations of the business model.


## Clear process

### Dev purpose

```
$ lsof -i tcp:3000
$ kill -9 PID
```

### Dev purpose

```.todo
- [X] Project setup.
- [X] Task 1: Add a REST endpoint to retrieve a list of beers
- [X] Task 2: Add a REST endpoint to allow a user to add a rating to a beer.
- [X] Task 3: Create an express middleware module.
- [X] Task 4: Unit tests Add applicable unit tests to the express middleware module in Task 3.
- [ ] Task 5: Add caching support.
```
