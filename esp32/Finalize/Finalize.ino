#include <HTTPClient.h>
#include <WiFi.h>
#include <UrlEncode.h>
#include "DFRobotDFPlayerMini.h"
#include "Secret.h"
#include "SoftwareSerial.h"

SoftwareSerial Ultra(35, 34); //RX, TX
#define LED1 5 // on and off
//#define LED2 18 // ultrasonic detection
#define LED3 19 // send whatsapp
// connection wifi
#define LED4 33 
#define LED5 32

//for motor
#define dir3 13 // motor right front
#define dir4 27 // motor right back
#define pwm3 12
#define pwm4 14

DFRobotDFPlayerMini player;
const int echo = 25;
const int trig = 26;

int duration = 0;
int distance = 0;

bool obstacleDetected = false;
unsigned long obstacleStartTime = 0;

int keyIndex = 0;     

bool ultrasRunning = false;

WiFiServer server(80);

void setup() {
  pinMode(13, OUTPUT); //motor
  pinMode(14, OUTPUT); //motor
  pinMode(12, OUTPUT); //motor 
  pinMode(27, OUTPUT); //motor 
  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);
  Serial.begin(115200); // initialize serial communication
  Ultra.begin(9600);
  pinMode(LED1, OUTPUT); // set the Relay pin mode
  //pinMode(LED2, OUTPUT);
  pinMode(LED3, OUTPUT);
  pinMode(LED4, OUTPUT);
  pinMode(LED5, OUTPUT);
  digitalWrite(LED1, LOW);
  //digitalWrite(LED2, LOW);
  digitalWrite(LED3, LOW);
  digitalWrite(LED4, LOW);
  digitalWrite(LED5, LOW); 
  // Init serial port for DFPlayer Mini using Serial2
  Serial2.begin(9600, SERIAL_8N1, 22, 23); // TX, RX pins on DF

   if (player.begin(Serial2)) {
    Serial.println("DFPlayer Mini initialized");
    // Set volume to maximum (0 to 30).
    player.volume(30);
  } else {
    Serial.println("Connecting to DFPlayer Mini failed!");
  }

  delay(10);

  WiFi.begin(ssid, pass);

  // attempt to connect to WiFi network:
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print("Network named: ");
    Serial.println(ssid);
  }

  printWifiStatus(); // print out the status
  ConnectionLED ();
  server.begin();
}


void loop() {
  AndroidServer();
}

void MainControlUltrasonic(){
  Ultrasonic();
  Ultrasonic1uno();

}

void Ultrasonic1uno(){  
  String ultradata = "";
  while (Ultra.available() >0)  // Receive the distance from arduino
  {
    ultradata += char(Ultra.read());
  }
    
  int value = ultradata.toInt();

  if (value >= 5 && value <= 20) {
    Serial.print("Success: ");
    Serial.println(value);
    stop(); // Do new void for 10 second waiting
    TrigSound();
    obstacleDetected = true;
    obstacleStartTime = millis();
    WhatsAppMessages("Animal detected!!!");
  } 
  else{
    obstacleDetected = false;
  }

  delay(200);
}

void Ultrasonic() {
  forward();
  digitalWrite(trig, LOW);
  delayMicroseconds(100000);
  digitalWrite(trig, HIGH);
  delayMicroseconds(100000);
  digitalWrite(trig, LOW);

  duration = pulseIn(echo, HIGH);
  distance = (duration/2) / 28.5;
  Serial.println(distance);
  
  if (distance >= 5 && distance <= 40){
    //digitalWrite (LED2, HIGH);
    stop(); // Do new void for 10 second waiting
    TrigSound();
    obstacleDetected = true;
    obstacleStartTime = millis();
    WhatsAppMessages("Animal detected!!!");
    delay(500);
  }
  else{
    //digitalWrite (LED2, LOW);
   // turn180Degrees();
    forward();
    obstacleDetected = false;
    Serial.println("Done Forward");
  }


  if (obstacleDetected)
  {
    unsigned long MaxTime = millis() - obstacleStartTime;
    turn180Degrees();
    Serial.println("Done turn");
  }
}

void stop() {
  analogWrite(pwm3, 0);
  analogWrite(pwm4, 0);
  delay(2000);
}

void forward() {
  digitalWrite(dir3, 0);   /// move forward for motor 3&4
  analogWrite(pwm3, 205);
  digitalWrite(dir4, 1);
  analogWrite(pwm4, 230);
  delay(100);
  //analogWrite(pwm3, 0);
  //analogWrite(pwm4, 0);
}
 
void turn180Degrees()       // Perform a 180-degree turn (adjust the turn duration as needed)
{

  digitalWrite(dir3, 1);   /// move forward for motor 3&4
  analogWrite(pwm3, 240);
  digitalWrite(dir4, 1);
  analogWrite(pwm4, 240);
  delay(3900);             // Adjust the delay based on the required turn duration

}

void TrigSound() {
  // Start communication with DFPlayer Mini
  player.play(1);
}

void WhatsAppMessages(String message)
{
    
    String url = "https://api.callmebot.com/whatsapp.php?phone=" + phoneNumber + "&text=" + urlEncode(message) + "&apikey=" + apiKey;   
    
    HTTPClient http;                                                            
    http.begin(url);                                                            
    
    http.addHeader("Content-Type", "application/x-www-form-urlencoded");        
  
    uint16_t httpResponseCode = http.POST(url);                                 
  
    if (httpResponseCode == 200)                                                
    {
        digitalWrite(LED3, HIGH);                                               
        Serial.print("Successfull send message");                          
        delay(2000);                                                            
        digitalWrite(LED3, LOW);                                                
    }
    else                                                                        
    {
        Serial.println("Error");                    
        Serial.print("Code HTTP: ");                                          
        Serial.println(httpResponseCode);                                       
    }
    http.end();                                                                 
} 

void ConnectionLED () {
  digitalWrite(LED4, HIGH);
  delay(1000);                                                            
  digitalWrite(LED5, HIGH); 
  delay(1000);
}

void AndroidServer() {
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  Serial.println("New Client."); 
  while(!client.available()) {
    delay(1);
  }

  String request = client.readStringUntil('\r');
  Serial.print(request);
  
  if(request.indexOf("/ledon") != -1 ) {
    digitalWrite(LED1, HIGH);
    Serial.println("LED1 turned on.");
    ultrasRunning = true;
  }
  else if(request.indexOf("/ledoff") != -1 ) {
    digitalWrite(LED1, LOW);
    Serial.println("LED1 turned off.");
    ultrasRunning = false;
  }

   client.println("HTTP/1.1 200 OK");
   client.println("Content-type:text/html");
   client.println("");
   
  if (ultrasRunning) {
   MainControlUltrasonic();
  }


  client.print(",");
  delay(1);
}


// This function to print you ip address in the serial monitor. Use this ip address inside your software application.
// Enter the ip address inside page IP callibration.
void printWifiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your board's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
  // print where to go in a browser:
  Serial.print("Now open this URL on your browser --> http://");
  Serial.println(ip);
}

