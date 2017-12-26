#include <DFRobot_sim808.h>
#include <SoftwareSerial.h>

#define PIN_TX    10
#define PIN_RX    11
SoftwareSerial mySerial(PIN_TX,PIN_RX);
DFRobot_SIM808 sim808(&mySerial);//Connect RX,TX,PWR,

float x;
float y;
char deviceId[7];
char latitude[10];
char longtitude[10];
//char http_cmd[] = "POST /device/send/gps-cordinates?deviceId=22233&x=4333332&y=223 HTTP/1.0\r\n\r\n";
char http_cmd[200] = "POST /device/send/gps-cordinates?deviceId=22233&x=";
char buffer[512];

void setup()
{
  mySerial.begin(9600);
  Serial.begin(9600);
  initializeConnection();
  delay(3000);  
  getGPSCordinates();
  checkTCPConnection();
  sendGPSCordinates();
  closeConnection();
}
void initializeConnection()
{
  while(!sim808.init())
  {
      delay(1000);
      Serial.print("Sim808 init error\r\n");
  }
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
  Serial.print("X=");
  Serial.println(sim808.GPSdata.lat);
  Serial.print("Y=");
  Serial.println(sim808.GPSdata.lon);
  dtostrf(x, 6, 2, longtitude);
  dtostrf(y, 6, 2, latitude);
  sim808.detachGPS();
  Serial.println("Close the GPS power success");
  strcat(http_cmd, latitude);
  strcat(http_cmd, "&y=");
  strcat(http_cmd, longtitude);
  strcat(http_cmd, " HTTP/1.0\r\n\r\n");
  Serial.println(http_cmd);
}

void sendGPSCordinates()
{
  sim808.send(http_cmd, sizeof(http_cmd)-1);
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
void loop()
{
}
