#include <DFRobot_sim808.h>
#include <SoftwareSerial.h>

#define PIN_TX    10
#define PIN_RX    11
SoftwareSerial mySerial(PIN_TX,PIN_RX);
DFRobot_SIM808 sim808(&mySerial);//Connect RX,TX,PWR,

float x;
float y;
long timeout = 60000;
char deviceId[6] = "cbr600";
char latitude[10];
char longtitude[10];
//char http_get_cmd[] = "GET /device/22233/receive/device-configuration HTTP/1.0\r\n\r\n";
char http_post_cmd2[] = "POST /device/send/gps-cordinates?deviceId=cbr600&x=1&y=1 HTTP/1.0\r\n\r\n";
char http_get_cmd[100] = "GET /device/";
char http_post_cmd[] = "POST /device/send/gps-cordinates?deviceId=";
char buffer[512];

void makePostChar()
{
  char http_post_cmd_with_deviceId[sizeof(http_post_cmd) + sizeof(deviceId)];
  strcat(http_post_cmd_with_deviceId, deviceId);
  Serial.println(sizeof(http_post_cmd_with_deviceId));
  char http_post_cmd_with_x[sizeof(http_post_cmd_with_deviceId) + 4]; //to be adjusted &x= [3] + [1]
  strcat(http_post_cmd_with_x, "&x=");
  strcat(http_post_cmd_with_x, "1");
  Serial.println(sizeof(http_post_cmd_with_x));
  char http_post_cmd_with_y[sizeof(http_post_cmd_with_x) + 4]; //to be adjusted &x= [3] + [1]
  strcat(http_post_cmd_with_y, "&y=");
  strcat(http_post_cmd_with_y, "1");
  Serial.println(sizeof(http_post_cmd_with_y));
  char http_post_cmd_with_protocol[sizeof(http_post_cmd_with_y) + sizeof(" HTTP/1.0\r\n\r\n")];
  strcat(http_post_cmd_with_protocol, " HTTP/1.0\r\n\r\n");
  Serial.println(http_post_cmd_with_protocol);
}

void setup()
{
  mySerial.begin(9600);
  Serial.begin(9600);
  initializeConnection();
  checkTCPConnection();
  makePostChar();

}

void loop()
{
  //getGPSCordinates();
  sendGPSCordinates();
  delay(timeout);
}

char * deblank(char *str)
{
  char *out = str, *put = str;

  for(; *str != '\0'; ++str)
  {
    if(*str != ' ')
      *put++ = *str;
  }
  *put = '\0';

  return out;
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
  strcat(http_post_cmd, deviceId);
  strcat(http_post_cmd, "&x=");
  strcat(http_post_cmd, latitude);
  strcat(http_post_cmd, "&y=");
  strcat(http_post_cmd, longtitude);
  strcat(http_post_cmd, " HTTP/1.0\r\n\r\n");
  Serial.println(http_post_cmd);
}

void getDeviceConfiguration()
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
}

void sendGPSCordinates()
{
  sim808.send(http_post_cmd, sizeof(http_post_cmd)-1);
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
