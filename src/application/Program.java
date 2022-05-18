package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the file path: ");
		String path = sc.next();
		System.out.println();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Sale> sale = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				
				String[] fields = line.split(",");
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);
				
				sale.add(new Sale(month, year, seller, items, total));
				
				line = br.readLine();
			}
			
			Map<String, Double> map = new HashMap<>();
			for(Sale x : sale) {
				map.put(x.getSeller(), 0.0);
			}
			
			for(String seller : map.keySet()) {
				Double total = sale.stream()
						.filter(s -> s.getSeller().equals(seller))
						.map(s -> s.getTotal())
						.reduce(0.0, (x, y) -> x + y);
				map.put(seller, total);
				System.out.println(seller + " - R$ " + String.format("%.2f", total));
			}
			
			
		}catch(IOException e) {
			System.out.println("Error: " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}
		
		sc.close();
	}

}
