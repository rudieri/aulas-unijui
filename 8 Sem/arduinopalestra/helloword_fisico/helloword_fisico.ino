int pinLed = 13;
int pinLed2 = 12;

void setup(){
 pinMode(pinLed,OUTPUT); 
 pinMode(pinLed2,OUTPUT);
}

void loop(){
 
 digitalWrite(pinLed,HIGH);
 delay(2000);
 
 digitalWrite(pinLed,LOW);
 delay(2000);
 
 digitalWrite(pinLed2,HIGH);
 delay(2000);
 
 digitalWrite(pinLed2,LOW);
 delay(2000);
  
}
