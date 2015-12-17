public class Car implements Comparable<Car>
{
	private String vin, color, make, model;
	private int price, mileage;
	
	public Car()
	{}
	
	//set make
	public void setMake(String carMake){make = carMake;}
	
	//set model
	public void setModel(String carModel){model = carModel;}
	
	//get model
	public String getModel(){return model;}
	
	//get make
	public String getMake(){return make;}
	
	//get vin #
	public String getVin(){return vin;}
	
	//get color
	public String getColor(){return color;}
	
	//set color
	public void setColor(String color){this.color = color;}
	
	//get price
	public int getPrice(){return price;}
	
	//set price
	public void setPrice(int price) {this.price = price;}
	
	//set mileage
	public void setMileage(int mileage) {this.mileage = mileage;}
	
	//get mileage
	public int getMileage(){return mileage;}
	
	//set vin #
	public void setVin(String newVin){vin = newVin;}
	
	public boolean isMake(String match)
	{
		if(match.equals(make)){return true;}
		else{return false;}
	}
	
	public boolean isModel(String match)
	{
		if(match.equals(model)){return true;}
		else{return false;}
	}
	
	public int compareMileage(Car other)
	{
		if(this.mileage > other.mileage){ return 1;}
		else if(this.mileage < other.mileage){ return -1;}
		else{ return 0;}
	}
	
	public int compareTo(Car other)
	{
		if(this.price > other.price){ return 1;}
		else if(this.price < other.price){ return -1;}
		else{ return 0;}
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("Vin # is " + vin + "\n");
		b.append("Price is $" + price + "\n");
		b.append("Mileage is " + mileage + "\n");
		b.append("Make is " + make + "\n");
		b.append("Model is " + model + "\n");
		b.append("Color is " + color + "\n");
		return b.toString();
	}
	
	//Sort by make and model
	
}