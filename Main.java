import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        List<Sales> salesList = new ArrayList<>();

        // Read data from the file and create Sales objects
        String fileName = "sales_data.csv";
        Pattern pattern = Pattern.compile(",");

        try {
            salesList = Files.lines(Paths.get(fileName))
                    .skip(1)
                    .filter(line -> !line.startsWith("ORDERNUMBER")) // Add this line to filter out incorrectly formatted lines
                    .map(line -> {
                        String[] fields = pattern.split(line);
                        Sales sale = new Sales();
                        sale.setOrderNumber(Integer.parseInt(fields[0]));
                        sale.setQuantityOrdered(Integer.parseInt(fields[1]));
                        sale.setPriceEach(Double.parseDouble(fields[2]));
                        sale.setOrderLineNumber(Integer.parseInt(fields[3]));
                        sale.setSales(Double.parseDouble(fields[4]));
                        sale.setOrderDate(fields[5]);
                        sale.setStatus(fields[6]);
                        sale.setQuarterId(Integer.parseInt(fields[7]));
                        sale.setMonthId(Integer.parseInt(fields[8]));
                        sale.setYearId(Integer.parseInt(fields[9]));
                        sale.setProductLine(fields[10]);
                        sale.setMsrp(Integer.parseInt(fields[11]));
                        sale.setProductCode(fields[12]);
                        sale.setCustomerName(fields[13]);
                        sale.setPhone(fields[14]);
                        sale.setAddressLine1(fields[15]);
                        sale.setAddressLine2(fields[16]);
                        sale.setCity(fields[17]);
                        sale.setState(fields[18]);
                        sale.setPostalCode(fields[19]);
                        sale.setCountry(fields[20]);
                        sale.setTerritory(fields[21]);
                        sale.setContactLastName(fields[22]);
                        sale.setContactFirstName(fields[23]);
                        sale.setDealSize(fields[24]);
                        return sale;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Perform calculations and output results
        performCalculations(salesList);
    }

    public static void performCalculations(List<Sales> salesList) {
        
        double totalSalesNY = salesList.stream()
                .filter(sale -> sale.getCity().equals("NYC"))
                .mapToDouble(Sales::getSales)
                .sum();
        System.out.println("¿Cuánto fue el total de ventas de New York? " + totalSalesNY);
    
        
        long classicCarsNY = salesList.stream()
                .filter(sale -> sale.getCity().equals("NYC") && sale.getProductLine().equals("Classic Cars"))
                .count();
        System.out.println("¿Cuántos autos clásicos vendió New York? " + classicCarsNY);
    
 
        double totalClassicCarsNY = salesList.stream()
                .filter(sale -> sale.getCity().equals("NYC") && sale.getProductLine().equals("Classic Cars"))
                .mapToDouble(Sales::getSales)
                .sum();
        System.out.println("¿Cuánto fue el total de ventas de Autos Clásicos en New York? " + totalClassicCarsNY);
    
         
        long motorcyclesNY = salesList.stream()
                .filter(sale -> sale.getCity().equals("NYC") && sale.getProductLine().equals("Motorcycles"))
                .count();
        System.out.println("¿Cuántas Motocicletas vendió New York? " + motorcyclesNY);
    
        
        double totalMotorcyclesNY = salesList.stream()
                .filter(sale -> sale.getCity().equals("NYC") && sale.getProductLine().equals("Motorcycles"))
                .mapToDouble(Sales::getSales)
                .sum();
        System.out.println("¿Cuánto fue el total de ventas de Motocicletas en New York? " + totalMotorcyclesNY);
    
        
        String topNYCustomer = salesList.stream()
                .filter(sale -> sale.getCity().equals("NYC"))
                .collect(Collectors.groupingBy(Sales::getCustomerName, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Not found");
        System.out.println("¿Cuál fue el cliente de New York qué más autos compró? " + topNYCustomer);
    
         
        String topCustomer = salesList.stream()
                .collect(Collectors.groupingBy(Sales::getCustomerName, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Not found");
        System.out.println("¿Cuál fue el cliente de todo el archivo qué más compró? " + topCustomer);
    
        
        String leastCustomer = salesList.stream()
                .collect(Collectors.groupingBy(Sales::getCustomerName, Collectors.counting()))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Not found");
        System.out.println("¿Cuál fue el cliente de todo el archivo qué menos compró? " + leastCustomer);
    }
    
}
