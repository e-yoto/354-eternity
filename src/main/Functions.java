package main;

public class Functions {
	public static final double E = 2.7182818284590452354;
	public static final double PI = 3.14159265358979323846;
	private static final double DEGREES_TO_RADIANS = 0.017453292519943295;
	private static final double RADIANS_TO_DEGREES = 57.29577951308232;

	//Matt stdev	
	public static double standardDeviation(double[] values) {
		double result = 0;
		double avg = average(values);
		for(int i = 0; i < values.length; i++) {
			result += (values[i]-avg)*(values[i]-avg);
		}
		result = squareRoot(result/values.length);
		return result;
	}
	
	public static double average(double[] x) {
		double sum = 0;
		for(int i = 0; i < x.length; i++) {
			sum += x[i];
		}
		return sum/x.length;
	}
		
	public static double squareRoot(double x) {
		double val = x/2;
		double newVal;
		double error;
		do {
			newVal = val - ((val*val - x)/(2*val));
			error = absVal(newVal - val);
			val = newVal;
		} while(error > 0.000001);
		return val;
	}

	public static double absVal(double x) {
		if(x < 0) {
			return x*-1;
		} else {
			return x;
		}
	}
	
	public static int factorial(int x) {
		int result = 1;
		for(int i = 1; i <= x; i++) {
			result *= i;
		}
		return result;
	}

	//Tarek log
	public static double log(double x, double base) throws Error {
	    if (x <= 0 || base <= 0 || base == 1)
	        throw new Error();
	    return ln(x) / ln(base);
	}

	public static double ln(double x) {
	    double old_sum = 0.0;
	    double denominator = 1.0;
	    double y = (x - 1) / (x + 1);
	    double y2 = y * y;
	    double sum = y;

	    while (sum != old_sum) {
	        old_sum = sum;
	        denominator += 2.0;
	        y *= y2;
	        sum += y / denominator;
	    }
	    return 2.0 * sum;
	}

	public static double log(double x) throws Error {
	    return log(x, 10);
	}
	
	//Emamnuel x^y
	public static double power(double x, double y) {
		double integer = absVal((int)y);
		double decimal = absVal(y) - integer;
		double decimalresult = 0;
		double result = 1;
		for(int i = 0; i < integer; i++) {
			result *= x;
		}
		if(decimal != 0) {
			for(int i = 0; i < 10; i++) {
				decimalresult += (power(ln(x),i)/factorial(i))*power(decimal,i);
			}
			result *= decimalresult;
		}
		if(y < 0) {
			result = 1/result;
		}
		return result;
	}
	
	//Ahmed arccos
	public static double semifactorial(int x) throws Error {
        if (x < 0)
            throw new Error("MATH ERROR");

        int result = 1;
        while (x > 0) {
            result *= x;
            x -= 2;
        }
        return result;
    }

    public static double arccosine(double x) throws Error {
        if (x < -1 || x > 1)
            throw new Error("MATH ERROR");
        
        if (x == 0)
            return PI/2;
        else if (x == 1)
            return 0;
        else if (x == -1)
            return PI;


        // Taylor Series (14th approximation)
        double result = x;

        for (int n = 1; n < 14; n++) {
            result += (semifactorial(2 * n - 1) / semifactorial(2 * n)) * (power(x, 2 * n + 1) / (2 * n + 1));
        }

        return (PI/2 - result);

    }
    
	//Sam
	public static double MAD(double[] numbers) {
        double mean = average(numbers);
        double[] deviations = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            deviations[i] = absVal(numbers[i] - mean);
        }
        double mad = average(deviations);
        return mad;
    }
	
	//Ethan
	public static double sinh(double x) {
	  return (power(E,x) - power(E,-x)) / 2.0;
	}
	
	//Hira 
	public static double exponential(double a, double x, double y) {
		return a*power(x,y);
	}
	
}
