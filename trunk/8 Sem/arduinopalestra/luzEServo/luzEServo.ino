#include <Servo.h> 

Servo servo;

void setup(){
 pinMode(6,OUTPUT);
 Serial.begin(9600);
 servo.attach(7);
}


void loop(){
  
 if(Serial.available() > 0){
   char ent = Serial.read();
   
   if(ent == 'l'){
     analogWrite(6,100);
   }
   if(ent == 'd'){
     analogWrite(6,0);  
   }
   
   if(ent == 'a'){
     servo.write(180);
   }
   if(ent == 'f'){
     servo.write(0);
   }
   
   
 }
  
  
}
