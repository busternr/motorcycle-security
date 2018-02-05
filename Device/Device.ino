#include <DFRobot_sim808.h>
#include <SoftwareSerial.h>
#include <string.h>
#include <stdlib.h>
#define PIN_TX    10
#define PIN_RX    11
#define MAX_RETRIES 100
SoftwareSerial mySerial(PIN_TX,PIN_RX);
DFRobot_SIM808 sim808(&mySerial);//Connect RX,TX,PWR,
const String deviceId = "cbr600";
String formatedGetConfigurationString;
char lat[120];
char lon[120];
float x;
float y;
float speedInKm;
boolean isParked;
boolean isStolen;
long int timeout;
int timesLooped = 0;

String formatPostGpsString(String deviceId)
{ 
  return String("POST /device/send/gps-cordinates?deviceId=" + deviceId + "&x=" + lat + "&y="+ lon + "&speed=" + speedInKm + " HTTP/1.0\r\n\r\n");
}

String formatGetConfigurationString(String deviceId)
{ 
  return String("GET /device/" + deviceId + "/receive/device-configuration  HTTP/1.0\r\n\r\n");
}

void formatTimeoutString(String timeoutString)
{ 
  timeoutString = timeoutString.substring(timeoutString.indexOf(":") + 1, timeoutString.length());
  timeout = timeoutString.toInt();
}

void setup()
{
  mySerial.begin(9600);
  Serial.begin(9600);
}

void loop()
{
  digitalWrite(2, HIGH);
  initializeConnection();
  initializeTCPConnection();
  if(timesLooped == 0)
  {
    formatedGetConfigurationString = formatGetConfigurationString(deviceId);
    getConfiguration(formatedGetConfigurationString);
  }
  else if(timesLooped %5 == 0 && timesLooped != 0) getConfiguration(formatedGetConfigurationString);
  else
  {
    Serial.print("Delaying ");
    Serial.println(timeout);
    delay(timeout);
    getGPSCordinates();
    String formatedPostGpsString = formatPostGpsString(deviceId);
    sendGPSCordinates(formatedPostGpsString);
  }
  timesLooped++;
  closeConnection();
}

void initializeConnection()
{
  int currentRetries = 0;
  while(!sim808.init())
  {
      Serial.print("Sim808 init error\r\n");
      if (++currentRetries >= MAX_RETRIES) break;
  }
  Serial.print("Sim808 init success\r\n");
}

void initializeTCPConnection()
{
  int currentRetries = 0;
  while(!sim808.connect(TCP,"130.204.140.70", 8080))
  {
    Serial.println("TCP connection error");
    if (++currentRetries >= MAX_RETRIES) break;
  }
  Serial.println("TCP connection success");
}

void closeConnection()
{
  sim808.close();
  sim808.disconnect();
}

void getGPSCordinates()
{
  int currentRetries = 0;
  if( sim808.attachGPS()) Serial.println("Open the GPS power success");
  else Serial.println("Open the GPS power failure");
  while(1)
  {
    if (sim808.getGPS()) 
    {
      x = sim808.GPSdata.lat;
      y = sim808.GPSdata.lon;
      speedInKm = sim808.GPSdata.speed_kph;
      dtostrf(x, 2, 7, lat);
      dtostrf(y, 2, 7, lon);
      Serial.print("latitude :");
      Serial.println(lat);
      Serial.print("longitude :");
      Serial.println(lon);
      Serial.print("speed_kph :");
      Serial.println(speedInKm);
      break;
    }
  }
  sim808.detachGPS();
  Serial.println("Close the GPS power success");
}

void getConfiguration(String ConfGetUrl)
{
  boolean done = false;
  int currentRetries = 0;
  char buffer[512];
  String timeoutString;
  char requestString[ConfGetUrl.length()+1];
  ConfGetUrl.toCharArray(requestString, ConfGetUrl.length()+1);
  while(done == false)
  {
    Serial.println("Executing");
    Serial.println(requestString);
    sim808.send(requestString, sizeof(requestString)-1);
    while (true)
    {
        int ret = sim808.recv(buffer, sizeof(buffer)-1);
        if (ret <= 0) Serial.println("fetch over..."); 
        if (++currentRetries >= MAX_RETRIES) break;
        buffer[ret] = '\0';
        Serial.print(buffer);
        done = true;
        break;
    }
    Serial.println("");
    for(int counter = 0; counter <= sizeof(buffer) - 1; counter++)
    {
      if(buffer[counter] == '"') 
      {
        while(buffer[counter] != ',')
        {
          timeoutString += buffer[counter];
          counter++;
        }
        formatTimeoutString(timeoutString);
        break;
      }
    }
  }
}

void sendGPSCordinates(String GPSPostUrl)
{
  boolean done = false;
  int currentRetries = 0;
  char buffer[512];
  char requestString[GPSPostUrl.length()+1];
  GPSPostUrl.toCharArray(requestString, GPSPostUrl.length()+1);
  while(done == false)
  {
    Serial.println("Executing");
    Serial.println(requestString);
    sim808.send(requestString, sizeof(requestString)-1);
    while (true)
    {
        int ret = sim808.recv(buffer, sizeof(buffer)-1);
        if (ret <= 0) Serial.println("fetch over...");
        if (++currentRetries >= MAX_RETRIES) break;
        buffer[ret] = '\0';
        Serial.print(buffer);
        done = true;
        break;
    }
    Serial.println("");
  }
}
