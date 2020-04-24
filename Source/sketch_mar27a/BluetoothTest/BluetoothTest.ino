#include <Stepper.h>
const int stepsPerRevolution = 200;
// change this to fit the number of steps per revolution
// for your motor
// initialize the stepper library on pins 8 through 11:
Stepper myStepper(stepsPerRevolution, 8, 9, 10, 11);

int data = 63;

void setup() {
Serial.begin(9600);
}

void loop() {
  int i = 1;
  while(true){
    if(Serial.available()){
      data = Serial.read();
      Serial.println(data);

    }
    if(i < data*8){
      i++;
    }else{
      i--;
    }


    /*
    if(data > 0){
      if(i <= data*10+100){
        i++; 
      }else{
        i--;
      }
      Serial.println(data);
    }*/
    myStepper.step(1);
    myStepper.setSpeed(i);
  }
}
