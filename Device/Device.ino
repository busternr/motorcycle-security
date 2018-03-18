#include <DFRobot_sim808.h>
#include <SoftwareSerial.h>
#include <EEPROM.h>
#include <string.h>
#include <stdlib.h>
#include "LowPower.h"

#define PIN_TX    10
#define PIN_RX    11
#define MAX_RETRIES 100

SoftwareSerial mySerial(PIN_TX,PIN_RX);
DFRobot_SIM808 sim808(&mySerial);

const String deviceId = "cbr600";
String token;
long int timeout;
int timesLooped = 0;

void setup()
{
  mySerial.begin(9600);
  Serial.begin(9600);
  char tokenToRead[165];
  if(EEPROM.get(1, tokenToRead) != 'null')
  {
    EEPROM.get(1, tokenToRead);
    String str(tokenToRead);
    token = str;
  }
}

void loop()
{
  initializeConnection();
  initializeTCPConnection();
  if(token.length() == 0)
  {
    char tokenToWrite[165];
    token.toCharArray(tokenToWrite, token.length()+1);
    getToken("GET /device/" + deviceId + "/receive/device-token  HTTP/1.0\r\n\r\n");
    token.toCharArray(tokenToWrite, token.length()+1);
    EEPROM.put(1, tokenToWrite);
    timesLooped--; //Just so we don't skip to execute our getConfiguration function.
    closeConnection();
  }
  else if(timesLooped == 0) getConfiguration("GET /device/" + deviceId + "/receive/device-configuration  HTTP/1.0\r\ndevice-token: " + token + "\n\r\r\n");
  else if(timesLooped %5 == 0 && timesLooped != 0) getConfiguration("GET /device/" + deviceId + "/receive/device-configuration  HTTP/1.0\r\ndevice-token: " + token + "\n\r\r\n");
  else
  {
    //Putting the SIM808 module to sleep
    int currentRetries = 0;
    
    sim808.sleep();
    //Putting Arduino to sleep
    delay(100); // just so this chinese shit doesn't make 9/11.
    for (int i = 0; i <= timeout -4; i=i+8)
    { 
      Serial.println("Sleeping now");
      sim808.sleep();
      LowPower.powerDown(SLEEP_8S, ADC_OFF, BOD_OFF);  
    }
    currentRetries = 0;
    sim808.wakeUp();
    Serial.println("just wokeup");
    getAndPostGPSCoordinates();
  }
  timesLooped++;
  closeConnection();
}

void resetDevice()
{
  closeConnection();
  timesLooped = 0;
}

void initializeConnection()
{
  int currentRetries = 0;
  while(!sim808.init())
  {
      Serial.print("Sim808 init error\r\n");
      if(++currentRetries >= MAX_RETRIES)
      {
        resetDevice(); 
        break;
      }
  }
  Serial.print("Sim808 init success\r\n");
}

void initializeTCPConnection()
{
  int currentRetries = 0;
  while(!sim808.connect(TCP,"130.204.140.70", 8080))
  {
    Serial.println("TCP connection error");
    if(++currentRetries >= MAX_RETRIES)
    {
      resetDevice(); 
      break;
    }
  }
  Serial.println("TCP connection success");
}

void closeConnection()
{
  sim808.close();
  sim808.disconnect();
}

void getAndPostGPSCoordinates()
{
  char lat[120];
  char lon[120];
  float x;
  float y;
  float speedInKm;
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
  String body = "{\n  \"deviceId\":\"" + deviceId + "\",\n  \"x\":" + lat + ",\n  \"y\":" + lon + ",\n  \"speed\":" + speedInKm + "\n}";
  int contentLength = body.length() + 1;
  sendGPSCoordinates("POST /device/send/gps-coordinates HTTP/1.0\ndevice-token: " + token + "\nContent-Length: " + contentLength + "\nContent-Type: application/json\n\n" + body);
}

void getToken(String ConfGetUrl)
{
  boolean done = false;
  int currentRetries = 0;
  char buffer[512];
  String tokenString;
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
        if(++currentRetries >= MAX_RETRIES)
        {
          resetDevice(); 
          break;
        }
        buffer[ret] = '\0';
        Serial.print(buffer);
        done = true;
        break;
    }
    Serial.println("");
    for(int counter = 0; counter <= sizeof(buffer) - 1; counter++)
    {
      if(buffer[counter] == 'e' && buffer[counter + 1] == 'n' && buffer[counter + 2] == ':') 
      {
        while(buffer[counter] != '\n')
        {
          tokenString += buffer[counter];
          counter++;
        }
        token = tokenString.substring(tokenString.indexOf(":") + 2, tokenString.length());
        break;
      }
    }
    Serial.println("");
  }
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
        if(++currentRetries >= MAX_RETRIES)
        {
          resetDevice(); 
          break;
        }
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
        timeoutString = timeoutString.substring(timeoutString.indexOf(":") + 1, timeoutString.length());
        timeout = timeoutString.toInt();
        break;
      }
    }
    Serial.println("");
  }
}

void sendGPSCoordinates(String GPSPostUrl)
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
        if(++currentRetries >= MAX_RETRIES)
        {
          resetDevice(); 
          break;
        }
        buffer[ret] = '\0';
        Serial.print(buffer);
        done = true;
        break;
    }
    Serial.println("");
  }
}
