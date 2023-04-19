# LiveRef
This is the final project of the course 236651 at the Technion.


LiveRef is an Eclipse plug-in that provides live refactoring of Java code, by suggesting candidates for “extract method refactoring” in real time while programming.

## Prerequisite

1. Eclipse IDE
2. JAVA JDK 17+

## Installation

1. Download "LiveRef build.jar"
2. Add the jar to your project as a plugin library

## How to use
1. In Eclipse, open your project, and open the file you want to edit
2. Save the file again to trigger the onChange event to start the plugin
3. The refactoring candidates will be displaye as markers next to the code in the left side
4. Select the lines of the candidate (manually), and apply "Extract Method" refactoring function using the built-in function in Eclipse  
5. Enjoy the liveness of the suggestions of our plugin :)

https://user-images.githubusercontent.com/63556300/225927081-b11549c6-65a1-4a4a-9208-f7b48a0fc1a3.mp4

