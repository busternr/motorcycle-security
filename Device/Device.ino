#include <DFRobot_sim808.h>
#include <SoftwareSerial.h>
#include <string.h>
#include <stdlib.h>

#define PIN_TX    10
#define PIN_RX    11
SoftwareSerial mySerial(PIN_TX,PIN_RX);
DFRobot_SIM808 sim808(&mySerial);//Connect RX,TX,PWR,

int x =32;
int y = 32;
long timeout = 60000;
String deviceId = "Rs1285";
char latitude[10];
char longtitude[10];
char http_post_cmd2[] = "POST /device/send/gps-cordinates?deviceId=cbr600&x=32&y=32 HTTP/1.0\r\n\r\n";
String http_post_cmd = "POST /device/send/gps-cordinates?deviceId=";
char buffer[512];

String formatGpsCordinates()
{ 
  Serial.println(http_post_cmd2);
  Serial.println(String("POST /device/send/gps-cordinates?deviceId=" + deviceId + "&x=" + x + "&y="+y+ " HTTP/1.0\r\n\r\n"));
  return String("POST /device/send/gps-cordinates?deviceId=" + deviceId + "&x=" + x + "&y="+y+ " HTTP/1.0\r\n\r\n");
}

void setup()
{
  mySerial.begin(9600);
  Serial.begin(9600);
  initializeConnection();
  checkTCPConnection();
  String formatedUrlForGpsCordinates = formatGpsCordinates();
  sendGPSCordinates(formatedUrlForGpsCordinates);

}

void loop()
{
  //getGPSCordinates();
//  sendGPSCordinates();
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
  if(!sim808.connect(TCP,"10.19.9.85", 8080)) Serial.println("Connect error");
  else Serial.println("Connect 130.204.140.70 success");
}

/*void getGPSCordinates()
{
  if( sim808.attachGPS()) Serial.println("Open the GPS power success");
  else Serial.println("Open the GPS power failure");
  x = sim808.GPSdata.lat;
  y = sim808.GPSdata.lon;
  Serial.print("X=");
  Serial.println(sim808.GPSdata.lat);
  Serial.print("Y=");
  Serial.println(sim808.GPSdata.lon);
  x = 32;
  y = 32;
  dtostrf(x, 2, 0, longtitude);
  dtostrf(y, 2, 0, latitude);
  sim808.detachGPS();
  Serial.println("Close the GPS power success");
}*/

/*void getDeviceConfiguration()
{
  strcat(http_get_cmd, deviceId);
  strcat(http_get_cmd, "/receive/device-configuration HTTP/1.0\r\n\r\n");
  Serial.println(http_get_cmd);
  sim808.send(http_get_cmd, sizeof(http_get_cmd)-1);
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
}*/

void sendGPSCordinates(String postUrlForGpsCordinates)
{
  char azSUmGei[postUrlForGpsCordinates.length()];
  postUrlForGpsCordinates.toCharArray(azSUmGei, postUrlForGpsCordinates.length());
  Serial.println(sizeof(http_post_cmd2));
  Serial.println(postUrlForGpsCordinates.length());
  Serial.println(azSUmGei);
  sim808.send(http_post_cmd2, sizeof(http_post_cmd2)-1);
  //sim808.send(azSUmGei, sizeof(azSUmGei));
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
