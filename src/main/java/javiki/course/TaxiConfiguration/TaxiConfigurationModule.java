package javiki.course.TaxiConfiguration;

public class TaxiConfigurationModule {
    private TaxiConfiguration taxiConfiguration;


    public TaxiConfiguration buildConfiguration() {
        return TaxiConfiguration.builder()
                .nearbyCarsFirstDistanceKm(3)
                .nearbyCarsSecondDistanceKm(8)
                .nearbyCarsThirdDistanceKm(21)
                .build();
    }

    public TaxiConfiguration getConfiguration() {
        if (taxiConfiguration == null) {
            taxiConfiguration = buildConfiguration();
        }
        return taxiConfiguration;
    }

    public void setTaxiConfiguration(TaxiConfiguration taxiConfiguration) {
        this.taxiConfiguration = taxiConfiguration;
    }
}
