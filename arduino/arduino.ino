#include <SoftwareSerial.h>

const int echo1 = 13;
const int trig1 = 12;

const int echo2 = 11;
const int trig2 = 10;

const int echo3 = 9;
const int trig3 = 8;

const int echo4 = 6;
const int trig4 = 7;

SoftwareSerial Ultra(2, 3); // RX, TX


int duration = 0;
int distance = 0;

void setup() {
  Serial.begin(115200);
  Ultra.begin(115200);
  pinMode(trig1, OUTPUT);
  pinMode(echo1, INPUT);
  pinMode(trig2, OUTPUT);
  pinMode(echo2, INPUT);
  pinMode(trig3, OUTPUT);
  pinMode(echo3, INPUT);
  pinMode(trig4, OUTPUT);
  pinMode(echo4, INPUT);
}
void loop() {
  ultrasonic1();
  ultrasonic2();
  ultrasonic3();
  ultrasonic4();
}

void ultrasonic1() {
  digitalWrite(trig1, LOW);
  delayMicroseconds(1000);
  digitalWrite(trig1, HIGH);
  delayMicroseconds(1000);
  digitalWrite(trig1, LOW);

  duration = pulseIn(echo1, HIGH);
  distance = (duration/2) / 28.5;
  Serial.print("U1: ");
  Serial.println(distance);
  Ultra.println(distance); //Send Distance to Esp32
  delay(500);
}

void ultrasonic2() {
  digitalWrite(trig2, LOW);
  delayMicroseconds(1000);
  digitalWrite(trig2, HIGH);
  delayMicroseconds(1000);
  digitalWrite(trig2, LOW);

  duration = pulseIn(echo2, HIGH);
  distance = (duration/2) / 28.5;
  Serial.print("U2: ");
  Serial.println(distance);
  Ultra.println(distance); //Send Distance to Esp32
  delay(500);
}


void ultrasonic3() {
  digitalWrite(trig3, LOW);
  delayMicroseconds(1000);
  digitalWrite(trig3, HIGH);
  delayMicroseconds(1000);
  digitalWrite(trig3, LOW);

  duration = pulseIn(echo3, HIGH);
  distance = (duration/2) / 28.5;
  Serial.print("U3: ");
  Serial.println(distance);
  Ultra.println(distance); //Send Distance to Esp32
  delay(500); 
}


void ultrasonic4() {
  digitalWrite(trig4, LOW);
  delayMicroseconds(1000);
  digitalWrite(trig4, HIGH);
  delayMicroseconds(1000);
  digitalWrite(trig4, LOW);

  duration = pulseIn(echo4, HIGH);
  distance = (duration/2) / 28.5;
  Serial.print("U4: ");
  Serial.println(distance);
  Ultra.println(distance); //Send Distance to Esp32
  delay(500);
}



