#include <stdlib.h>
#include <string.h>
const int POT = 130;

String inputString = "";         
boolean stringCompleta = false; 


int echo = 11;
int trin = 12;
int distAnt = 0;

void setup() {
  pinMode(2,OUTPUT);
  pinMode(3,OUTPUT);  
  pinMode(4,OUTPUT);
  pinMode(5,OUTPUT);
  pinMode(6,OUTPUT);
  pinMode(7,OUTPUT);
  pinMode(8,OUTPUT);
  pinMode(9,OUTPUT);
  pinMode(10,OUTPUT);
  Serial.begin(9600);
  
  pinMode(echo, INPUT);  
  pinMode(trin, OUTPUT);
}

void loop(){  

  if(stringCompleta){
    if(inputString=="0") {
      apaga();
      num0(); }    
    if(inputString=="1") {
      apaga();
      num1(); }
    if(inputString=="2") {
      apaga();
      num2(); }
    if(inputString=="3") {
      apaga();
      num3(); }
    if(inputString=="4") {
      apaga();
      num4(); }
    if(inputString=="5") {
      apaga();
      num5(); }
    if(inputString=="6") {
      apaga();
      num6(); }
    if(inputString=="7") {
      apaga();
      num7(); }
    if(inputString=="8") {
      apaga();
      num8(); }
    if(inputString=="9") {
      apaga();
      num9(); }
    if(inputString=="10") {
      apaga();
      num10(); }
    if(inputString=="11") {
      apaga();
      num11(); }
    if(inputString=="12") {
      apaga();
      num12(); }
    if(inputString=="13") {
      apaga();
      num13(); }
    if(inputString=="14") {
      apaga();
      num14(); }
    if(inputString=="15") {
      apaga();
      num15(); }
    if(inputString=="16") {
      apaga();
      num16(); }
    if(inputString=="17") {
      apaga();
      num17(); }
    if(inputString=="18") {
      apaga();
      num18(); }
    if(inputString=="19") {
      apaga();
      num19(); }
    inputString="";
    stringCompleta = false;    
  }
    ultraSonic();
}

void num0(){
  analogWrite(2,POT);
  analogWrite(3,POT);
  analogWrite(5,POT);
  analogWrite(6,POT);
  analogWrite(7,POT);
  analogWrite(8,POT);
}

void num1(){ 
  digitalWrite(5,HIGH);
  digitalWrite(7,HIGH);  
}

void num2(){ 
  digitalWrite(2,HIGH);
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(7,HIGH);
}

void num3(){ 
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(7,HIGH);
}

void num4(){ 
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(7,HIGH);
  digitalWrite(8,HIGH);
}

void num5(){ 
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(8,HIGH);
}

void num6(){ 
  digitalWrite(2,HIGH);
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(8,HIGH);
}

void num7(){ 
  digitalWrite(3,HIGH);
  digitalWrite(5,HIGH);  
  digitalWrite(7,HIGH);
}

void num8(){ 
  digitalWrite(2,HIGH);
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(7,HIGH);
  digitalWrite(8,HIGH);
}

void num9(){ 
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(7,HIGH);
  digitalWrite(8,HIGH);
}

void num10(){ 
  digitalWrite(2,HIGH);
  digitalWrite(3,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(7,HIGH);
  digitalWrite(8,HIGH);
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);
}

void num11(){ 
  digitalWrite(5,HIGH);
  digitalWrite(7,HIGH);
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);
}

void num12(){ 
  digitalWrite(2,HIGH);
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(7,HIGH);
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);
}

void num13(){ 
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(7,HIGH);
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);
}

void num14(){ 
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(7,HIGH);
  digitalWrite(8,HIGH);
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);
}

void num15(){ 
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(8,HIGH);
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);
}

void num16(){ 
  digitalWrite(2,HIGH);
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(8,HIGH);
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);
}

void num17(){ 
  digitalWrite(3,HIGH);
  digitalWrite(5,HIGH);  
  digitalWrite(7,HIGH);
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);  
}

void num18(){ 
  digitalWrite(2,HIGH);
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(7,HIGH);
  digitalWrite(8,HIGH);
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);
}

void num19(){ 
  digitalWrite(3,HIGH);
  digitalWrite(4,HIGH);
  digitalWrite(5,HIGH);
  digitalWrite(6,HIGH);
  digitalWrite(7,HIGH);
  digitalWrite(8,HIGH);
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);
}

void apaga() {
  analogWrite(2,LOW);
  analogWrite(3,LOW);
  analogWrite(4,LOW);
  analogWrite(5,LOW);
  analogWrite(6,LOW);
  analogWrite(7,LOW);
  analogWrite(8,LOW);
  analogWrite(9,LOW);
  analogWrite(10,LOW);
}

void ultraSonic(){
  //seta o pino 12 com um pulso baixo "LOW" ou desligado ou ainda 0  
    digitalWrite(trin, LOW);  
  // delay de 2 microssegundos  
    delayMicroseconds(2);  
  //seta o pino 12 com pulso alto "HIGH" ou ligado ou ainda 1  
    digitalWrite(trin, HIGH);  
  //delay de 10 microssegundos  
    delayMicroseconds(10);  
  //seta o pino 12 com pulso baixo novamente  
    digitalWrite(trin, LOW);  
  //pulseInt lê o tempo entre a chamada e o pino entrar em high  
    long duration = pulseIn(echo,HIGH);  
  //Esse calculo é baseado em s = v . t, lembrando que o tempo vem dobrado  
  //porque é o tempo de ida e volta do ultrassom  
    long distancia = duration /29 / 2 ;  
   
  int vel = abs(distAnt-distancia)*0.75;
  //Serial.print("Dist");
//  Serial.println(distancia);
  Serial.println(vel); 
  distAnt = distancia;
  char xxx[2];
  itoa(vel,xxx,8);
  Serial.println(xxx);
  stringCompleta = true;
  inputString = xxx;
  

  delay(1000); //espera 1 segundo para fazer a leitura novamente  
}

void serialEvent() {
  while (Serial.available()) {
    char inChar = (char)Serial.read();    
    if (inChar == '\n' || inChar == '\r') {
      stringCompleta = true;
    }   
    else{
      inputString += inChar;
    }
  }
}
