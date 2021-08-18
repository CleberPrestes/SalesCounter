package br.eti.arthurgregorio.contadorvendas;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

//UTFPR — Universidade Tecnológica Federal do Paraná UTFPR
//XXI Curso de Especialização em Tecnologia Java (2021_01)
//Linguagem De Programação Java II
//Atividade B4A1
//Cleber dos Santos Prestes de Oliveira


public class SalesReader {

    private final List<Sale> sales;

    public SalesReader(String salesFile) {

        final var dataStream = ClassLoader.getSystemResourceAsStream(salesFile);

        if (dataStream == null) {
            throw new IllegalStateException("File not found or is empty");
        }

        final var builder = new CsvToBeanBuilder<Sale>(new InputStreamReader(dataStream, StandardCharsets.ISO_8859_1));

        sales = builder
                .withType(Sale.class)
                .withSeparator(';')
                .build()
                .parse();
    }

    public void totalCompletedSales() {
        // TODO mostrar o total (em R$) de vendas completas - ok

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        final var totalSales =  sales.stream().filter(sales -> sales.getStatus()
                .equals("Concluída")).map(Sale::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.printf("#1 -Total de vendas concluidas:  %s", toCurrency(totalSales)).println();


    }

    public void totalCancelledSales() {
        // TODO mostrar o total (em R$) de vendas canceladas -ok
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        final var totalSalesCancel =  sales.stream().filter(sales -> sales.getStatus()
                .equals("Cancelada")).map(Sale::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        System.out.printf("#2-Total de vendas canceladas:  %s", toCurrency(totalSalesCancel)).println();

    }

    public void mostRecentSale() {
        // TODO encontrar qual foi a data da primeira venda -ok
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        final var mostRecentSale = sales.stream().sorted(Comparator
                .comparing(Sale::getSaleDate).reversed()).findFirst().get().getSaleDate();

        System.out.printf("#3 Data da venda mais recente:  %s", mostRecentSale).println();


    }

    public void daysBetweenFirstAndLast() {
        // TODO calcular qual a quantidade de dias entre a primeira e a ultima venda-ok
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        final var firstSale = sales.stream().sorted(Comparator.comparing(Sale::getSaleDate)).findFirst()
                .get().getSaleDate();

        final var finalSale = sales.stream().sorted(Comparator.comparing(Sale::getSaleDate).reversed())
                .findFirst().get().getSaleDate();

       final var daysBetween = ChronoUnit.DAYS.between(firstSale, finalSale);

        System.out.printf("#4 Quantidade de dias entre primeira e ultima venda:  %s", daysBetween).println();

    }

    public void totalSalesBySeller(String sellerName) {
        // TODO encontrar o total (em R$) de vendas do vendedor recebido por parametro-ok

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        final var totalSalesBySeller =  sales.stream().filter(sales -> sales.getSeller()
                .equals(sellerName)).map(Sale::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.printf("#5 Total do valor de vendas por vendedor: %s - %s",sellerName ,toCurrency(totalSalesBySeller))
                .println();

    }

    public void countSalesByManager(String managerName) {
        // TODO contar a quantidade de vendas para o gerente recebido por parametro - ok

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        final var totalSalesBySeller = sales.stream().filter(sales -> sales.getManager()
                .equals(managerName)).count();

        System.out.printf("#6 Total de vendas por gerente:  %s - quantidade: %s",managerName ,totalSalesBySeller)
                .println();
    }

    public void totalSalesByMonth(Month... months) {
        // TODO totalizar o valor (em R$) de vendas para os meses informados por parametro -ok

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        final var totalSalesByMonth =  sales.stream()
                .filter(sale -> Arrays.asList(months).contains(sale.getSaleDate().getMonth())).map(Sale::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.printf("#7 Total do valor de vendas nos meses: %s valor %s ", Arrays.asList(months),toCurrency(totalSalesByMonth)).println();


    }

    public void rankingByDepartment() {
        // TODO faca um ranking contando o total (quantidade) de vendas por departamento-ok

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        final var departamentList = sales.stream().map(Sale::getDepartment)
                .distinct().collect(Collectors.toList());

        Map<String, Long> orderList = new HashMap<>();

        for (String depart : departamentList
        ) {
            final var rankingByDepartment = sales.stream()
                    .filter(sale -> sale.getDepartment().equals(depart)).count();
                orderList.put(depart, rankingByDepartment);
        }
        System.out.println("#8 Ranking de quantidade de vendas por departamento");
        orderList.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(System.out::println);

      }

    public void rankingByPaymentMethod() {
        // TODO faca um ranking contando o total (quantidade) de vendas por meio de pagamento-ok

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        final var payList = sales.stream().map(Sale::getPaymentMethod).distinct().collect(Collectors.toList());

          Map<String, Long> orderList = new HashMap<>();

        for (String payType : payList
             ) {

            final var rankingByPaymentMethod = sales.stream().filter(sale -> sale.getPaymentMethod().equals(payType))
                    .count();

            orderList.put(payType, rankingByPaymentMethod);
        }

        System.out.println("#9 Ranking de quantidade de vendas por meio de pagamento");
        orderList.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(System.out::println);


    }

    public void bestSellers() {
        // TODO faca um top 3 dos vendedores que mais venderam (ranking por valor em vendas)-ok
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("#10 - Ranking por valor em vendas");

        final var salesForSellers = sales.stream()
                .filter(sales -> sales.getStatus().equals("Concluída")).collect(Collectors
                        .groupingBy(Sale::getSeller, Collectors.mapping(Sale::getValue, Collectors.reducing(
                                BigDecimal.ZERO, BigDecimal::add))));
        salesForSellers.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(3)
                .forEach(bestSellers -> System.out.println(bestSellers.getKey() + " vendas: " + toCurrency(bestSellers.getValue())));

    }

    /*
     * Use esse metodo para converter objetos BigDecimal para uma represetancao de moeda
     */
    private String toCurrency(BigDecimal value) {
        return NumberFormat.getInstance().format(value);
    }
}
