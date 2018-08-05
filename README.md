# ControllerLibDashboard
Java program for facilitating communication between USB controller and arduino. Designed to work with USBControllerLib Arduino library at https://github.com/NicholasBerryman/USBControllerLib

## Installation
### Windows
Download and run the .exe installer from https://github.com/NicholasBerryman/ControllerLibDashboard/raw/master/dist/bundles/ArduinoControllerDashboard-1.0.exe
### Other Operating Systems
A .jar file is provided at https://github.com/NicholasBerryman/ControllerLibDashboard/raw/master/dist/ArduinoControllerDashboard.jar
This file should be able to run on any machine with a recent java installation

## Usage
### Dashboard
Plug in any USB controllers and Arduino boards before starting.  
Upon opening, a windows will appear prompting you to select a COM port. The user should select the COM port of the Arduino board that they wish to send USB controller data to. If no COM port is selected then the program will continue, though it will not send any information to any USB port.  
Once a COM port has been selected and confirmed, the main dashboard window will appear. At the bottom of this window the user is given a list of available controllers. Selecting any of these controllers and pressing the 'Add Controller' button will tell the program to start tracking that controller, and adds a tab to the top of the program containing representations of the controller's components (buttons and axes). Any number of controllers may be added. Controller components are assigned an identifying number and are colour coded based on type and value as follows:  
  * Button: Red = not pressed, green = pressed  
  * POV: Red = not pressed, green = leftmost part of POV pressed
    * *Note* Colour gradually changes from red to green as the POV is pressed in a clockwise direction from its leftmost part
  * Axis: blue = 0, red = -1, green = 1  
    * *Note* Intermediate colours are used for non-integral values  
    
Below the controller tab is the messages box, which displays messages and logs sent from the arduino.  
Under the messages box is another text box with a send button next to it. These  controls are used to send messages to the Arduino.

### Arduino
Before attempting to use the library, the Dashboard::begin() function must be called.  
Additionally, the Dashboard::updateData() function must be called in the main loop to ensure consistent controller status between the Arduino and the main dashboard program.  
Once these conditions are satisfied, Controller objects can be retrieved using the `Controller& Dashboard::GetController(int ID)` function. The ID of a controller is simply the number in which it was added to the main dashboard program (i.e. the first controller added has ID 0, the second has ID 1, etc.  
Once a controller object has been retrieved, component statuses may be polled at any time via the `boolean controllerObj.getButton(int ID)`, `float controllerObj.getAxis(int ID)` `float controllerObj.getPOV(int ID)` methods. In each case the ID of a given axis, button, or POV is the same as the number overlaying its representation on the main dashboard.  
Messages can be sent to the main dashboard via the `Dashboard::println(String)` function, and messages from the dashboard may be read and subsequently cleared using the `String Dashboard::getMessage` and `void Dashboard::clearMessage` functions respectively.
Logging can be enabled and disabled through the `void Dashboard::startLogging()` and `void Dashboard::stopLogging()` functions respectively. If logging is enabled then any changes to any controller state as read by the arduino is sent to the main dashboard's message box. This can be useful to confirm the program is functioning correctly and you have the correct IDs, but can often drown out other messages, so it is disabled by default.  
