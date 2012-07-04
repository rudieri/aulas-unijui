
#include <Servo.h>
Servo servoCam; // Define  Servo
int posCam = 180; //POsicao Camera Inicial

int S1 = 5;     //V1 Controle Velocidade
int S2 = 6;     //V2 Controle Velocidade
int V1 = 4;    //V1 Direcao 
int V2 = 7;    //V1 Direcao

//
String inputString = "";         
boolean stringCompleta = false;  


void stop(void)                    //Parar
{
  digitalWrite(S1,LOW);  
  digitalWrite(S2,LOW);     
}  
void frente(char a,char b)          //Move forward
{
  analogWrite (S1,a);      //PWM Speed Control
  digitalWrite(V1,LOW);   
  analogWrite (S2,b);   
  digitalWrite(V2,LOW);
} 
void atras (char a,char b)          //Move backward
{
  analogWrite (S1,a);
  digitalWrite(V1,HIGH);  
  analogWrite (S2,b);   
  digitalWrite(V2,HIGH);
}
void girar_esquerda (char a,char b)             //Turn Left
{
  analogWrite (S1,a);
  digitalWrite(V1,HIGH);   
  analogWrite (S2,b);   
  digitalWrite(V2,LOW);
}
void girar_direita (char a,char b)             //Turn Right
{
  analogWrite (S1,a);
  digitalWrite(V1,LOW);   
  analogWrite (S2,b);   
  digitalWrite(V2,HIGH);
}

void manual (char sentido1, int potencia1,char sentido2,int potencia2)             //Turn Right
{
  Serial.print("Paramentos Manual: ");
  Serial.print(sentido1);
  Serial.print(";");
  Serial.print(potencia1);
  Serial.print(";");
  Serial.print(sentido2);
  Serial.print(";");
  Serial.println(potencia2);

  analogWrite (S1,potencia1);
  digitalWrite(V1,sentido1);   
  analogWrite (S2,potencia2);   
  digitalWrite(V2,sentido2);
   Serial.print("Deu Certo"); 
  
}

void setup(void)
{

  servoCam.attach(13);
  servoCam.write(0);

  int i;
  for(i=4;i<=7;i++)
    pinMode(i, OUTPUT); 
  Serial.begin(9600);      //Set Baud Rate
  Serial.println("Starteo....");
  inputString.reserve(200);
}
void loop(void){
  //Recebe Caracter Normal
  if(stringCompleta){
    if(inputString.length()==1){
      Serial.println(inputString);
      char val = inputString.charAt(0);
      Serial.print("Comando Direto: ");
      Serial.println(val);

      if(val != -1)
      {
        switch(val)
        {
        case 'w'://Pra Frente
          frente (175,175);   //move forward in max speed        
          break;
        case 's'://Tra Tras
          atras (175,175);   //move back in max speed
          break;
        case 'a'://Pra Esquerda
          girar_esquerda (175,175);
          break;      
        case 'd'://Pra Direita
          girar_direita (175,175);
          break;
        case 'x': // Para
          stop();
          break;
        case '+'://camera ++
          posCam= posCam+20;
          if(posCam>180)
            posCam = 180;
          servoCam.write(posCam);
          Serial.println("Camera "+posCam);
          break;
        case '-'://camera ++
          posCam= posCam-20;
          if(posCam<0)
            posCam = 0;
          servoCam.write(posCam);
          Serial.println("Camera "+posCam);
          break;
        }
      }
      else stop(); 
      inputString = "";
      stringCompleta = false;
    }
    if(inputString.length()>1){

      if(inputString.charAt(0)=='+' || inputString.charAt(0)=='-'){
        //Recebe Potencia Motores
        // +99-30  ou  -99-99

        int sentido1 = inputString.charAt(0)=='+'?LOW:HIGH;
        Serial.println(inputString.substring(1,3));
        int potencia1 = int(StringToInt(inputString.substring(1,3))*2.55);

        int sentido2 = inputString.charAt(3)=='+'?LOW:HIGH;
        int potencia2 = int(StringToInt(inputString.substring(4,6))*2.55);

        //Manda Potencia Manual
        manual(sentido1,potencia1,sentido2,potencia2);
        Serial.print("Recebi Potencia Motor: ");
        Serial.println(inputString);

      }

      if(inputString.charAt(0)=='c'){
        //Cotrole Camera
        //C000  // C090 // C180       
        posCam = StringToInt(inputString.substring(1,3));
        servoCam.write(posCam);
        Serial.print("Recebi Posicao Camera: ");
        Serial.println(posCam);

      }    
      inputString = "";
      stringCompleta = false;
    }
  }
}

void serialEvent() {
  while (Serial.available()) {
    char inChar = (char)Serial.read();    
    if (inChar == '\n' || inChar == '\r') {
      stringCompleta = true;
    }
    else if(inChar == 'w'|| inChar == 'a'
    || inChar == 's'|| inChar == 'd'
      || inChar == 'x') {
      Serial.println("Modo_Teclado");
      inputString += inChar;
      stringCompleta = true;
    }   
    else{
      inputString += inChar;
    }
  }
}

int StringToInt(String st) {
  //Serial.println("StringToInt");
  //Serial.println(st);
  //Serial.println("to");

  char aux[st.length()] ;
  for(int i =0; i < st.length();i++){
    aux[i] = st.charAt(i);
  }

  //Serial.print(aux);
  //Serial.println(atoi(aux));

  return atoi(aux);

}







