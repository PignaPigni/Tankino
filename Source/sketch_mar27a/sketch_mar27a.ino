/* Stepper Motor Control */

#include <Stepper.h>
const int stepsPerRevolution = 200;
// change this to fit the number of steps per revolution
// for your motor
// initialize the stepper library on pins 8 through 11:
Stepper myStepper(stepsPerRevolution, 8, 9, 10, 11);

void setup() {
   // set the speed at 60 rpm:
   myStepper.setSpeed(50);
   // initialize the serial port:
   Serial.begin(9600);
}

void loop() {
   // step one revolution in one direction:
   //Serial.println("clockwise");
   int i = 50;
   while(true){
     myStepper.step(1); 
     myStepper.setSpeed(i);
     if(i <= 1200){
      i++;   
     }
   }
   //delay(100);
}
