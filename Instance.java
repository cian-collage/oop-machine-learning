package machine_learning;

class Instance {
    private final double hoursPlayed;
    private final double HourToPriceRatio;
    private final boolean windows;
    private final boolean mac;
    private final boolean linux;
    private final boolean recommended;

    public Instance(double hoursPlayed, double HourToPriceRatio, boolean windows, boolean mac, boolean linux, boolean recommended) {
        this.hoursPlayed = hoursPlayed;
        this.HourToPriceRatio = HourToPriceRatio;
        this.windows = windows;
        this.mac = mac;
        this.linux = linux;
        this.recommended = recommended;
    }

    // Getters for the features
    public double getHoursPlayed() {
        return hoursPlayed;
    }

    public double getHourToPriceRatio() {
        return HourToPriceRatio;
    }

    public boolean isWindows() {
        return windows;
    }

    public boolean isMac() {
        return mac;
    }

    public boolean isLinux() {
        return linux;
    }

    public boolean isRecommended() {
        return recommended;
    }
}
