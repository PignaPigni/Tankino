#include <Thread.h>
#include <ThreadController.h>
#include <Stepper.h>

const int stepsPerRevolution = 400;
Stepper leftStepper(stepsPerRevolution, 52, 50, 48, 46);
Stepper rightStepper(stepsPerRevolution, 34, 32, 30, 28);
Thread leftStepperThread;
Thread rightStepperThread;

int data = 63;
int leftCeling = 63;
int rightCeling = 63;
int leftStepperSpeed = 1;
int rightStepperSpeed = 1;

void setup() {
  Serial.begin(9600);
  leftStepperThread.setInterval(data);
  leftStepperThread.onRun(leftStepperLoop);
  
  rightStepperThread.setInterval(data);
  rightStepperThread.onRun(rightStepperLoop);
}

void leftStepperLoop(){
  leftStepper.step(1);
}

void rightStepperLoop(){
  rightStepper.step(-1);
}

void loop() {
    if(rightStepperThread.shouldRun()){
      rightStepperThread.run();
    }
    if(leftStepperThread.shouldRun()){
      leftStepperThread.run();
    }
    if(Serial.available()){
      data = Serial.read();
      if(data <= 127){
        leftCeling = data;
      }else{
        rightCeling = data-128;
       }
    }
    if(leftStepperSpeed <= leftCeling*8+1){ 
      leftStepperSpeed++;
    }else{
      leftStepperSpeed--;
    }
    if(rightStepperSpeed <= rightCeling*8+1){ 
      rightStepperSpeed++;
    }else{
      rightStepperSpeed--;
    }
    leftStepperThread.setInterval(10-(double)(leftStepperSpeed)/(double)(1018)*(double)(100)/10);
    leftStepper.setSpeed(leftStepperSpeed);
    rightStepperThread.setInterval(10-(double)(rightStepperSpeed)/(double)(1018)*(double)(100)/10);
    rightStepper.setSpeed(rightStepperSpeed);
}
