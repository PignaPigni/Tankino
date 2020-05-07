#include <Thread.h>
#include <ThreadController.h>

#include <Stepper.h>


const int stepsPerRevolution = 400;

//Stepper myStepper(stepsPerRevolution, 52, 50, 48, 46);

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
  //while(true){
     leftStepper.step(1);
     
  //}
}

void rightStepperLoop(){
  //while(true){   
     rightStepper.step(-1);
  //}
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
       // Serial.println(leftCeling);
      }else{
        rightCeling = data-128;
        //Serial.println(rightCeling);
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
    //Serial.println(10-(double)(leftStepperSpeed)/(double)(1018)*(double)(100)/10);

    /*int ritardo = 10-(double)(i)/(double)(1018)*(double)(100);
    Serial.println(ritardo);*/
    leftStepperThread.setInterval(10-(double)(leftStepperSpeed)/(double)(1018)*(double)(100)/10);
    leftStepper.setSpeed(leftStepperSpeed);
    //leftStepperThread.setInterval(10-(double)(leftStepperSpeed)/(double)(1018)*(double)(100)/10);
    rightStepperThread.setInterval(10-(double)(rightStepperSpeed)/(double)(1018)*(double)(100)/10);
    rightStepper.setSpeed(rightStepperSpeed);
    
}
