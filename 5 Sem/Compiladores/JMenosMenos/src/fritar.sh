#/bin/bash
echo "Limpado Classes Java...\n"
sleep 1
rm  $1.java
rm  $1.class
echo "Construindo Classe Java...\n"
sleep 1
javacc J--
echo "Compilando Classe Java...\n"
sleep 1
javac JMenos.java
echo "Executar...\n"
sleep 1
java JMenos $1.j--
javac $1.java
java $1


