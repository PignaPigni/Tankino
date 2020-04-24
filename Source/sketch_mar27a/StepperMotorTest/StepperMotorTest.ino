/* Stepper Motor Control */

#include <Stepper.h>
const int stepsPerRevolution = 200;
// change this to fit the number of steps per revolution
// for your motor
// initialize the stepper library on pins 8 through 11:
Stepper myStepper(stepsPerRevolution, 8, 9, 10, 11);

void setup() {
   // initialize the serial port:
   Serial.begin(9600);
}

void loop() {
   // step one revolution in one direction:
   //Serial.println("clockwise");
   
   
   int i = 50;
   int data = 0;
   while(true){
    if(Serial.available()){
          //Serial.println("DATA: " + data);
         data = Serial.read();
         Serial.print("DATA: ");
         Serial.println(data);
         //Serial.println("I: " + i);
    }
    Serial.print("DATA: ");
    
     myStepper.step(1); 

     if(i <= data*10+100){
      i++;
     }else{
       i = data;
     }

     myStepper.setSpeed(i);
   }
   /*
   while(true){
     myStepper.step(1); 

     if(i <= 1000){
      i++;
     }else{
      i--;
     }     

     myStepper.setSpeed(i);
   }*/
   
}
