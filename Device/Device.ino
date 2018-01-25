#include <DFRobot_sim808.h>
#include <SoftwareSerial.h>
#include <string.h>
#include <stdlib.h>

#define PIN_TX    10
#define PIN_RX    11
SoftwareSerial mySerial(PIN_TX,PIN_RX);
DFRobot_SIM808 sim808(&mySerial);//Connect RX,TX,PWR,

//==========DeviceId==========\\
const String deviceId = "cbr600";
//==========DeviceId==========\\

double x;
double y;
double speedInKm;
long timeout;
boolean isParked;
boolean isStolen;

char buffer[512];

String formatPostGpsString(String deviceId)
{ 
  return String("POST /device/send/gps-cordinates?deviceId=" + deviceId + "&x=" + x + "&y="+ y + "&speed=" + speedInKm + " HTTP/1.0\r\n\r\n");
}

String formatGetConfigurationString(String deviceId)
{ 
  return String("GET /device/" + deviceId + "/receive/device-configuration  HTTP/1.0\r\n\r\n");
}

void setup()
{
  String deviceId = "cbr600";
  mySerial.begin(9600);
  Serial.begin(9600);
  initializeConnection();
  checkTCPConnection();
  getGPSCordinates();
  String formatedGetConfigurationString = formatGetConfigurationString(deviceId);
  getConfiguration(formatedGetConfigurationString);
  String formatedPostGpsString = formatPostGpsString(deviceId);
  sendGPSCordinates(formatedPostGpsString);
}

void loop()
{
  delay(timeout);
}

void initializeConnection()
{
  while(!sim808.init())
  {
      delay(1000);
      Serial.print("Sim808 init error\r\n");
  }
  Serial.print("Sim808 init success\r\n");
}

void checkTCPConnection()
{
  if(!sim808.connect(TCP,"130.204.140.70", 8080)) Serial.println("Connect error");
  else Serial.println("Connect 130.204.140.70 success");
}

void getGPSCordinates()
{
  if( sim808.attachGPS()) Serial.println("Open the GPS power success");
  else Serial.println("Open the GPS power failure");
  x = sim808.GPSdata.lat;
  y = sim808.GPSdata.lon;
  speedInKm = sim808.GPSdata.speed_kph;
  Serial.print("X=");
  Serial.println(x);
  Serial.print("Y=");
  Serial.println(y);
  Serial.print("Speed=");
  Serial.println(speedInKm);
  sim808.detachGPS();
  Serial.println("Close the GPS power success");
}

void getConfiguration(String ConfGetUrl)
{
  
  //TODO Parse json information and store it into variables: timeout, isParked, isStolen
  
  char requestString[ConfGetUrl.length()+1];
  ConfGetUrl.toCharArray(requestString, ConfGetUrl.length()+1);
  Serial.println("Executing");
  Serial.println(requestString);
  sim808.send(requestString, sizeof(requestString)-1);
  while (true) {
      int ret = sim808.recv(buffer, sizeof(buffer)-1);
      if (ret <= 0){
          Serial.println("fetch over...");
          break; 
      }
      buffer[ret] = '\0';
      Serial.print(buffer);
      break;
  }
}

void sendGPSCordinates(String GPSPostUrl)
{
  char requestString[GPSPostUrl.length()+1];
  GPSPostUrl.toCharArray(requestString, GPSPostUrl.length()+1);
  Serial.println("Executing");
  Serial.println(requestString);
  sim808.send(requestString, sizeof(requestString)-1);
  while (true) {
      int ret = sim808.recv(buffer, sizeof(buffer)-1);
      if (ret <= 0){
          Serial.println("fetch over...");
          break; 
      }
      buffer[ret] = '\0';
      Serial.print(buffer);
      break;
  }
}

void closeConnection()
{
  sim808.close();
  sim808.disconnect();
}
