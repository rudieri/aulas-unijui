void setup(){
  Serial.begin(9600);
}

void loop(){
  if(Serial.available()>0 && Serial.read() == 'o'){
    
    Serial.println("Hello Word");
  }
}
