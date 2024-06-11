# TUIdemo Project

# Dvorovenko Max

This project demonstrates a Text User Interface (TUI) application for a bank, built using Java.

## Project Setup

1. Download the necessary JAR files: `jline`, `jansi`, and `MyBank`.
2. Create a new project named `TUIdemo` in NetBeans without a main class.
3. Add the downloaded JAR files to the project's libraries: Right-click on the project, select Properties > Libraries > Add JAR/Folder.
4. Add the `CLIdemo.java` file from this repository to the project.
5. Study the source code in the `CLIdemo.java` file to understand how it works.

## Running the Application

1. Open a console (terminal or PowerShell) and navigate to the project directory.
2. Run the project using the command `java -jar TUIdemo.jar`.
3. You should see a prompt `(bank>)`.
4. Use TAB to view available commands, type `h TAB ENTER` for help, `cus TAB s ENTER` to view the list of bank customers, `Up Backspace Space 1 ENTER` to view information about customer number 1, `Up 2 ENTER` to test incorrect customer number handling, and `e TAB ENTER` to exit.
5. Ensure that the application behaves as expected.

![image](https://github.com/ppc-ntu-khpi/tui-2-snowaaaaaaaaaa/assets/144525592/2765eaf5-b751-4642-8dfc-e2fd079cecb1)

## Additional Command: `report`

Add a new command `report` to the application, which should display a report of customers similar to the CustomerReport in Task 8.

![image](https://github.com/ppc-ntu-khpi/tui-2-snowaaaaaaaaaa/assets/144525592/b4baa770-0bdc-4503-850a-e4b59b315417)

## Testing

1. Run the project and ensure everything works as expected.
2. Demonstrate the functionality to the instructor.

