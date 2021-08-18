package br.eti.arthurgregorio.contadorvendas;

import java.time.Month;

public class Application {

    public Application() {
        startReading();
    }

    private void startReading() {

        final SalesReader salesReader = new SalesReader("arquivo-dados.csv");

        salesReader.totalCompletedSales();
        salesReader.totalCancelledSales();
        salesReader.mostRecentSale();
        salesReader.daysBetweenFirstAndLast();
        salesReader.totalSalesBySeller("Adriana Gomes");
        salesReader.countSalesByManager("Elenice Mendes");
        salesReader.totalSalesByMonth(Month.JULY, Month.SEPTEMBER);
        salesReader.rankingByDepartment();
        salesReader.rankingByPaymentMethod();
        salesReader.bestSellers();
    }

    public static void main(String[] args) {
        new Application();
    }
}
