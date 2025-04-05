package javiki.course.TaxiConfiguration;

public class TaxiConfiguration {
    private final int nearbyCarsFirstDistanceKm;
    private final int nearbyCarsSecondDistanceKm;
    private final int nearbyCarsThirdDistanceKm;

    public TaxiConfiguration(int nearbyCarsFirstDistanceKm, int nearbyCarsSecondDistanceKm, int nearbyCarsThirdDistanceKm) {
        this.nearbyCarsFirstDistanceKm = nearbyCarsFirstDistanceKm;
        this.nearbyCarsSecondDistanceKm = nearbyCarsSecondDistanceKm;
        this.nearbyCarsThirdDistanceKm = nearbyCarsThirdDistanceKm;
    }

    public static TaxiConfigurationBuilder builder() {
        return new TaxiConfigurationBuilder();
    }

    //чтобы не писать билдер вручную есть библиотека Lombok
    public static class TaxiConfigurationBuilder {
        private int nearbyCarsFirstDistanceKm;
        private int nearbyCarsSecondDistanceKm;
        private int nearbyCarsThirdDistanceKm;

        public TaxiConfigurationBuilder nearbyCarsFirstDistanceKm(int nearbyCarsFirstDistanceKm) {
            this.nearbyCarsFirstDistanceKm = nearbyCarsFirstDistanceKm;
            return this;
        }

        public TaxiConfigurationBuilder nearbyCarsSecondDistanceKm(int nearbyCarsSecondDistanceKm) {
            this.nearbyCarsSecondDistanceKm = nearbyCarsSecondDistanceKm;
            return this;
        }

        public TaxiConfigurationBuilder nearbyCarsThirdDistanceKm(int nearbyCarsThirdDistanceKm) {
            this.nearbyCarsThirdDistanceKm = nearbyCarsThirdDistanceKm;
            return this;
        }

        public TaxiConfiguration build() {
            return new TaxiConfiguration(nearbyCarsFirstDistanceKm, nearbyCarsSecondDistanceKm, nearbyCarsThirdDistanceKm);
        }
    }

    public int getNearbyCarsFirstDistanceKm() {
        return nearbyCarsFirstDistanceKm;
    }

    public int getNearbyCarsSecondDistanceKm() {
        return nearbyCarsSecondDistanceKm;
    }

    public int getNearbyCarsThirdDistanceKm() {
        return nearbyCarsThirdDistanceKm;
    }
}
