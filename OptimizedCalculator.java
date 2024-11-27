// OptimizedCalculator.java
public class OptimizedCalculator {
    private int[] numbers;

    public OptimizedCalculator(int[] numbers) {
        this.numbers = numbers;
    }

    // Optimized method to calculate the sum
    public int calculateSum() {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    // Method to find the maximum number
    public int findMax() {
        int max = numbers[0];
        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5};
        OptimizedCalculator calculator = new OptimizedCalculator(data);
        System.out.println("Sum: " + calculator.calculateSum());
        System.out.println("Max: " + calculator.findMax());
    }
}
