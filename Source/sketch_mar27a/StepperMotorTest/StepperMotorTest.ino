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
   digitalWrite(4, 0);
   digitalWrite(5, 1);
   Serial.begin(9600);
}

void loop() {
   // step one revolution in one direction:
   //Serial.println("clockwise");
   
   
   int i = 50;
   int data = 50;
   /*while(true){
    if(Serial.available()){
         data = Serial.read();
     Serial.println(data);
    }
     delay(2000);      
   }*/
   while(true){
    if(Serial.available()){
         data = Serial.read();
          //Serial.println("DATA: " + data);
         //Serial.println("I: " + i);
    }
     myStepper.step(1); 

     if(i <= data*10+100){
      i++;
     }else{
      i--;
     }     

     myStepper.setSpeed(i);
   }
   //delay(100);
}
