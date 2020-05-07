#include <Stepper.h>
const int stepsPerRevolution = 200;
// change this to fit the number of steps per revolution
// for your motor
// initialize the stepper library on pins 8 through 11:
//Stepper myStepper(stepsPerRevolution, 8, 9, 10, 11);
  Stepper leftStepper(stepsPerRevolution, 52, 50, 48, 46);
  Stepper rightStepper(stepsPerRevolution, 34, 32, 30, 28);

int data = 63;

void setup() {
Serial.begin(9600);
}

void loop() {

  int leftSpeed = 10;
  int rightSpeed = 0;
  int i = 0;
  
  while(true){
    if(Serial.available()){
      data = Serial.read();
    }
    if(i <= data*8+1){
      i++;
    }else{
      i--;
    }

    leftStepper.step(1);
    leftStepper.setSpeed(i);
    
    rightStepper.step(-1);
    //setRightSpeed(rightSpeed);
    rightStepper.setSpeed(i);
    
  }
}
/*
void setRightSpeed(int vel){
  rightStepper.setSpeed(vel);  
  
}*/
