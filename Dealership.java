import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Dealership
{
	static PQ<Car> data;
	static Scanner inScan;
	
	public static void main(String [] args)
	{
		data = new PQ<Car>();
		inScan = new Scanner(System.in);
		System.out.println("Welcome to the Car Dealership!!");
		String choice = "";
		while(!choice.equals("q")){
			System.out.println("\nTo add a car type: 'a'");
			System.out.println("To update a car type: 'u'");
			System.out.println("To remove a car type: 'd'");
			System.out.println("To get lowest price car type: 'lp'");
			System.out.println("To get lowest mileage car type: 'lm'");
			System.out.println("To get the lowest price car by make and model type: 'lpm'");
			System.out.println("To get the lowest mileage car by make and model type: 'lmm'");
			choice = inScan.next();
			switch(choice){
				case "a":
					addCar();
					break;
				case "u":
					System.out.println("\nPlease input the VIN number");
					String temp = inScan.next();
					if(data.myMap.containsKey(temp)){
						updateCar(temp);
					}
					else{
						System.out.println("Sorry that is an invalid VIN");
					}
					break;
				case "d":
					System.out.println("\nPlease input the VIN number");
					String temp1 = inScan.next();
					if(data.myMap.containsKey(temp1)){
						deleteCar(temp1);
					}
					else{
						System.out.println("Sorry that is an invalid VIN");
					}
					break;
				case "lp":
					lowestPrice();
					break;
				case "lm":
					lowestMileage();
					break;
				case "lpm":
					lowestPriceCar();
					break;
				case "lmm":
					lowestMileageCar();
					break;
			}
		}
	}
	
	public static void addCar(){ //Add a car
		Car newCar = new Car(); //Create new Car
		System.out.println("\nPlease input the VIN number");
		boolean uniqueVin = false;
		String tempVin = "";
		while(uniqueVin != true){ //Ensure the car has a unique VIN
			tempVin = inScan.next();
			if(data.myMap.containsKey(tempVin) || tempVin.contains("I") || tempVin.contains("O") || tempVin.contains("Q")){
				System.out.println("Sorry the VIN is invalid, please choose another");
			}
			else{uniqueVin = true;}
		}
		newCar.setVin(tempVin); //Car has unique vin, set it
		//Make
		System.out.println("\nPlease input the car's make"); 
		newCar.setMake(inScan.next());
		//Model
		System.out.println("\nPlease input the car's model"); 
		newCar.setModel(inScan.next());
		//Price
		System.out.println("\nPlease input the car's price"); 
		newCar.setPrice(inScan.nextInt());
		//Mileage
		System.out.println("\nPlease input the car's mileage"); 
		newCar.setMileage(inScan.nextInt());
		//Color
		System.out.println("\nPlease input the car's color"); 
		newCar.setColor(inScan.next());
		//Add to PQ
		data.insert(newCar, newCar.getVin());
	}

	public static void updateCar(String carVin){	//Update a car 
		Car update = data.getCar(carVin);
		System.out.println("Would you like to update the price of the car? (y or n)"); //Possibly update price
		if(inScan.next().equals("y")){
			System.out.println("What is the new price?");
			update.setPrice(inScan.nextInt());
		}
		System.out.println("Would you like to update the mileage of the car? (y or n)"); //Possibly update mileage
		if(inScan.next().equals("y")){
			System.out.println("What is the new mileage?");
			update.setMileage(inScan.nextInt());
		}
		System.out.println("Would you like to update the color of the car? (y or n)"); //Possibly update color
		if(inScan.next().equals("y")){
			System.out.println("What is the new color?");
			update.setColor(inScan.next());
		}

	}
	
	public static void deleteCar(String carVin){
		data.delMiddle(data.myMap.get(carVin));
		data.myMap.remove(carVin);
	}
	
	public static void lowestPrice(){	//Retrieve lowest price
		if(!data.isEmpty()){
			data.sort(0);
			System.out.println("\n" + data.min().toString());
		}
		else{ System.out.println("Sorry the dealership is empty...");}
	}
	
	public static void lowestMileage(){
		if(!data.isEmpty()){
			data.sort(1);
			System.out.println("\n" + data.min().toString());
		}
		else{ System.out.println("Sorry the dealership is empty...");}
	}
	
	public static void lowestPriceCar(){
		System.out.println("Please input the make of the car");
		String make = inScan.next();
		System.out.println("Please input the model of the car");
		String model = inScan.next();
		data.getLowestPrice(make,model);
	}
	
	public static void lowestMileageCar(){
		System.out.println("Please input the make of the car");
		String make = inScan.next();
		System.out.println("Please input the model of the car");
		String model = inScan.next();
		data.getLowestMileage(make,model);
	}
	
}