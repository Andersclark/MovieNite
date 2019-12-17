```java

class Program
ArrayList<Car> cars = new ArrayList<>(); // flera bilar


class Car
ArrayList<Wheel> wheels = new ArrayList<>(); // 4st


class Wheel 
int durability = 100;

public boolean isMaxDurability() {
  return durability == 100;
}


ArrayList<Car> maxDurabilityCars = new ArrayList<>(); // flera bilar

for(Car car : cars) {

  int wheelCounter = 0;

  for(Wheel wheel : car.getWheels){

    int thisWheelDurability = wheel.getDurability()

    if(thisWheelDurability == 100) {
      wheelCounter++;
    }
  }


  if(wheelCounter == 4) {
    maxDurabilityCars.add(car);
  }

}

















```