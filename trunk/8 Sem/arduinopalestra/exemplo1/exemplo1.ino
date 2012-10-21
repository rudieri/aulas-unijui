#include <Servo.h>

int pinLed = 13;
int pinServo = 10;
Servo porta;
void setup(){

  Serial.begin(9600); 
  pinMode(pinLed,OUTPUT);
  porta.attach(pinServo);
  
}


void loop(){
  if(Serial.available()){
    char o = Serial.read();
    if(o=='l'){
       digitalWrite(pinLed, HIGH); 
    }else if (o=='d'){
      digitalWrite(pinLed, LOW); 
    }else if(o =='a'){
      porta.write(180);
    } else if(o =='f'){
      porta.write(0);
    }    
    
    
    
  }
  
}
