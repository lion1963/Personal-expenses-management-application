# Personal-expenses-management-application

To launch this application you need to:
1) Have Maven on your computer
2) Clone this repository to your computer
3) Invoke command "mvn compile"
4) Invoke command "mvn exec:java -Dexec.mainClass="com.sviatoslav.app.Application" to run application
5) Application is ready to use. To get information about all commands invoke "help" in command line

add {yyyy-mm-dd} {price} {currency} {name} - save expense to database;
list - shows the list of all expenses;
clear {yyyy-mm-dd} - removes all expenses for specified date;
total {currency} - calculate the total amount of money spent and present it to user in specified currency(currency rates are taken from fixer.io)
exit - end the work of programm.
