
int data;
int led1 = 11;

void setup() {
pinMode(led1, OUTPUT);
Serial.begin(9600);
}

void loop() {
  if(Serial.available()){
    int data = Serial.read();
    Serial.println(data);
    if(data == 49){
      digitalWrite(led1, 1);
      Serial.println("LED ON");
    }else if(data == 50){
      digitalWrite(led1, 0);
      Serial.println("LED ON");
    }
    delay(100);
  }
}
