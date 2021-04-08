# Vending Machine Exercise

## Introduction

`VendingMachineSim` is a sample Java application that implements the business logic for operating a simple vending machine.
* Note: All error handling are placed with error code 500 internal server error for consistency.
* Start date: 202104080000
* V0: 202104080010 Project setup.
* V1: 202104080312 Simulation tested.


## Source
Exercise link:
* https://github.com/wyjwyj1104/VendingMachineSim/blob/main/2021_Q2_java_exercise_vending_machine.docx


## Prerequisite

* [Java 10](https://www.oracle.com/ca-en/java/technologies/java-archive-javase10-downloads.html) Required to install Java SE 10.
* [JavaDoc](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html) Reference to JavaDoc for documentation.



## Setup and running the app

```
$ javac VendingMachineSim.java
$ java VendingMachineSim.java
```

## Test

1. Run the app.
2. Displays the vending machine data.
3. Program asks user to select the slot number. ("Welcom! Please select slot number!")
4. Select a slot number (For testing file, can choose from 1-7).
5. Program asks user to select the quantity of the items to buy, if previous input is valid. ("Please select item quantity (10)")
6. Select a quantity number (For testing file slot 1, quantity can choose from 1-10).
7. Program asks user to insert money to the machine, if previous input is valid. ("Total price is $0.99, Please insert change $0.05, $0.10, $0.25, $1.00, $5.00")
8. Users can only put $0.05, $0.10, $0.25, $1.00, $5.00 into the machine input.
9. If user inserts sufficient money to buy the total items (quantity * item price), the items will be vended out from the machine.
10. Return remaining changes to the user. ("Vending 1 SnickerBar, returning remainder of $0!")
11. Return to step 1.


### Dev purpose

```.todo
- [X] Project setup.
- [X] Task 1: Architecting data models.
- [X] Task 2: Loading initial testing data.
- [X] Task 3: Planning expecting output and work flow.
- [X] Task 4: Implementation.
- [ ] TODO: Unit test.
- [X] Task 5: Main loop logic.
- [X] Task 6: Cleaning and refactoring.
- [X] Task 7: User inputs.
- [X] Task 8: User testing.
- [X] Task 9: Review and upload.
```
