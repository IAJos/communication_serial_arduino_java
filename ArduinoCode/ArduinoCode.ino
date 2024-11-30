#include <ArduinoJson.h>

void setup() {

  Serial.begin(9600);
  delay(5000);
}

void loop() {

  receiveData();
  delay(5000);
}

int getData(int min, int max) {
    return min + (rand() % (max - min + 1));
}

void receiveData() {
  StaticJsonDocument<100> jsonBuffer;
  jsonBuffer["device_name"] = "ESP";
  jsonBuffer["quantityCO2"] = getData(50, 100);
  jsonBuffer["quantityCO"] = getData(0, 40);
  jsonBuffer["quantityNO2"] = getData(0, 40);
  jsonBuffer["quantityO3"] = getData(0, 40);
  jsonBuffer["fine_particle"] = getData(0, 40);
  jsonBuffer["temperature"] = getData(0, 40);
  String jsonString;
  serializeJson(jsonBuffer, jsonString);
  Serial.println(jsonString);
}
