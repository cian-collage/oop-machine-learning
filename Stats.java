package machine_learning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats {

    private final Map<String, Double> probabilities;

    private final Map<String, Double> statistics;

    public Stats() {
        probabilities = new HashMap<>();
        statistics = new HashMap<>();
    }
    // Method to calculateStatistics the classifier and calculate statistics

    public Map<String, Double> calculateStatistics(List<Instance> data) {
        // Calculate probabilities
        int totalInstances = data.size();
        int totalRecommended = 0;

        // Count total recommended instances
        for (Instance instance : data) {
            if (instance.isRecommended()) {
                totalRecommended++;
            }
        }

        // Calculate probability of being recommended and not recommended
        double probabilityRec = (double) totalRecommended / totalInstances;
        double probabilityNotRec = 1.0 - probabilityRec;

        // Store probabilities
        probabilities.put("recommended", probabilityRec);
        probabilities.put("not_recommended", probabilityNotRec);

        // Initialize variables for statistics
        int totalReviews = data.size();
        int recCount = 0;
        int notRecCount = 0;

        double totalHoursRec = 0;
        double totalHoursNotRec = 0;

        double totalLogHoursRec = 0;
        double totalLogHoursNotRec = 0;

        double pricePerHourRec = 0;
        double pricePerHourNotRec = 0;

        double logPricePerHourRec = 0;
        double logPricePerHourNotRec = 0;

        int winCountRec = 0;
        int macCountRec = 0;
        int linuxCountRec = 0;

        int winCountNotRec = 0;
        int macCountNotRec = 0;
        int linuxCountNotRec = 0;

        
        // Calculate statistics
        for (Instance instance : data) {
            double hoursPlayed = instance.getHoursPlayed();
            double pricePerHour = instance.getHourToPriceRatio();

            double hoursPlayedlog;
            double pricePerHourlog;

            if (hoursPlayed != 0) {
                hoursPlayedlog = Math.log(hoursPlayed);

            } else {
                // Set hoursPlayed to a default value
                hoursPlayed = 0.0000001; //default value
                hoursPlayedlog = 0.0000001;
            }

            if (pricePerHour != 0) {
                pricePerHourlog = Math.log(pricePerHour);

            } else {
                // Set hoursPlayed to a default value
                pricePerHour = 0.0000001; //default value
                pricePerHourlog = 0.0000001;
            }

            if (instance.isRecommended()) {
                recCount++;
                pricePerHourRec += pricePerHour;
                totalHoursRec += hoursPlayed;
                logPricePerHourRec += pricePerHourlog;
                totalLogHoursRec += hoursPlayedlog;


                if (instance.isWindows()) {
                    winCountRec++;
                }
                if (instance.isMac()) {
                    macCountRec++;
                }
                if (instance.isLinux()) {
                    linuxCountRec++;
                }
            } else {
                notRecCount++;
                pricePerHourNotRec += pricePerHour;
                totalHoursNotRec += hoursPlayed;
                logPricePerHourNotRec += pricePerHourlog;
                totalLogHoursNotRec += hoursPlayedlog;

                if (instance.isWindows()) {
                    winCountNotRec++;
                }
                if (instance.isMac()) {
                    macCountNotRec++;
                }
                if (instance.isLinux()) {
                    linuxCountNotRec++;
                }
            }
        }

        // Calculate averages
        double avgLogHoursRec = recCount != 0 ? totalLogHoursRec / recCount : 0;
        double avgLogHoursNotRec = notRecCount != 0 ? totalLogHoursNotRec / notRecCount : 0;

        double avgLogPricePerHourRec = recCount != 0 ? logPricePerHourRec / recCount : 0;
        double avgLogPricePerHourNotRec = notRecCount != 0 ? logPricePerHourNotRec / notRecCount : 0;

        double avgHoursRec = recCount != 0 ? totalHoursRec / recCount : 0;
        double avgHoursNotRec = notRecCount != 0 ? totalHoursNotRec / notRecCount : 0;

        double avgPricePerHourRec = recCount != 0 ? pricePerHourRec / recCount : 0;
        double avgPricePerHourNotRec = notRecCount != 0 ? pricePerHourNotRec / notRecCount : 0;


        // Calculate percentages
        double percentRec = (double) recCount / totalReviews * 100;
        double percentNotRec = (double) notRecCount / totalReviews * 100;

        // Calculate platform percentages for recommended reviews
        double percentWinRec = ((double) winCountRec / totalReviews) * 100;
        double percentMacRec = ((double) macCountRec / totalReviews) * 100;
        double percentLinuxRec = ((double) linuxCountRec / totalReviews) * 100;

        // Calculate platform percentages for not recommended reviews
        double percentWinNotRec = ((double) winCountNotRec / totalReviews) * 100;
        double percentMacNotRec = ((double) macCountNotRec / totalReviews) * 100;
        double percentLinuxNotRec = ((double) linuxCountNotRec / totalReviews) * 100;

        // Store statistics
        statistics.put("totalReviews", (double) totalReviews);
        statistics.put("recCount", (double) recCount);
        statistics.put("notRecCount", (double) notRecCount);
        statistics.put("percentRec", percentRec);
        statistics.put("percentNotRec", percentNotRec);

        statistics.put("avgLogHoursRec", avgLogHoursRec);
        statistics.put("avgLogHoursNotRec", avgLogHoursNotRec);
        statistics.put("avgLogPricePerHourRec", avgLogPricePerHourRec);
        statistics.put("avgLogPricePerHourNotRec", avgLogPricePerHourNotRec);

        statistics.put("avgHoursRec", avgHoursRec);
        statistics.put("avgHoursNotRec", avgHoursNotRec);
        statistics.put("avgPricePerHourRec", avgPricePerHourRec);
        statistics.put("avgPricePerHourNotRec", avgPricePerHourNotRec);

        statistics.put("percentWinRec", percentWinRec);
        statistics.put("percentMacRec", percentMacRec);
        statistics.put("percentLinuxRec", percentLinuxRec);
        statistics.put("percentWinNotRec", percentWinNotRec);
        statistics.put("percentMacNotRec", percentMacNotRec);
        statistics.put("percentLinuxNotRec", percentLinuxNotRec);

        return statistics;
    }
    // Method to predict if a game is recommended
// Method to predict if a game is recommended

    public boolean predict(Instance instance) {
        // Calculate likelihoods
        double likelihoodRecommended = 1.0;
        double likelihoodNotRecommended = 1.0;
        // Calculate likelihood based on features
        likelihoodRecommended *= calculateRecommendedLikelihood (instance.getHoursPlayed(), instance.getHourToPriceRatio(), instance.isWindows(), instance.isMac(), instance.isLinux());
        likelihoodNotRecommended *= calculateNotRecommendedLikelihood (instance.getHoursPlayed(), instance.getHourToPriceRatio(), instance.isWindows(), instance.isMac(), instance.isLinux());

        // Calculate posterior probabilities
        double posteriorRecommended = likelihoodRecommended * probabilities.get("recommended");
        double posteriorNotRecommended = likelihoodNotRecommended * probabilities.get("not_recommended");

        if (posteriorRecommended > posteriorNotRecommended) {
            return true;
        } else {
            return false;
        }
    }

    // Method to calculate likelihood of features given class

    private double calculateRecommendedLikelihood(double hoursPlayed, double hourToPriceRatio, boolean windows, boolean mac, boolean linux) {
        double recCount = statistics.get("recCount");
        double percentRec = statistics.get("percentRec");
        double avgHoursNotRec = statistics.get("avgLogHoursNotRec");
        double avgHoursRec = statistics.get("avgLogHoursRec");
        double avgPricePerHourNotRec = statistics.get("avgLogPricePerHourNotRec");
        double avgPricePerHourRec = statistics.get("avgLogPricePerHourRec");
        double percentWinRec = statistics.get("percentWinRec");
        double percentMacRec = statistics.get("percentMacRec");
        double percentLinuxRec = statistics.get("percentLinuxRec");

        // Convert hours played to log form
        double HoursPlayed = Math.log(hoursPlayed) ;

        // Likelihood of hours played | Recommended
        double P_Hours = HoursPlayed / (HoursPlayed * avgHoursNotRec);
        System.out.println("HoursPlayed "+HoursPlayed);
        System.out.println("avgHoursNotRec "+avgHoursNotRec);
        System.out.println("(HoursPlayed * avgHoursNotRec) "+(HoursPlayed * avgHoursNotRec));




        double RecommendedCount_given_H = (P_Hours * recCount) / ((avgHoursRec / (HoursPlayed * avgHoursRec)) *recCount);
        double hoursPlayedRec  = (RecommendedCount_given_H * P_Hours) / recCount;

        // Likelihood of avgPricePerHour | Recommended
        double P_PricePerHours = avgPricePerHourRec / (hourToPriceRatio + avgPricePerHourNotRec);
        double recCount_given_P_H = (P_PricePerHours * recCount) / ((avgPricePerHourRec / (hourToPriceRatio + avgPricePerHourRec)) * recCount);
        double pricePerHourRec   = (recCount_given_P_H * P_PricePerHours) / recCount;

        // Likelihood of Windows | Recommended
        double likelihoodWinGivenRec;
        if (windows) {
            likelihoodWinGivenRec = percentWinRec / 100.0;
        } else {
            likelihoodWinGivenRec = 1 - (percentWinRec / 100.0);
        }

        // Likelihood of mac | Recommended
        double likelihoodMacGivenRec;
        if (mac) {
            likelihoodMacGivenRec = percentMacRec / 100.0;
        } else {
            likelihoodMacGivenRec = 1 - (percentMacRec / 100.0);
        }

        // Likelihood of Linux | Recommended
        double likelihoodLinuxGivenRec;
        if (linux) {
            likelihoodLinuxGivenRec = percentLinuxRec / 100.0;
        } else {
            likelihoodLinuxGivenRec = 1 - (percentLinuxRec / 100.0);
        }

        return (percentRec / 100) * hoursPlayedRec * pricePerHourRec * likelihoodWinGivenRec * likelihoodMacGivenRec * likelihoodLinuxGivenRec;
    }

    private double calculateNotRecommendedLikelihood(double hoursPlayed, double hourToPriceRatio, boolean windows, boolean mac, boolean linux) {
        double notRecCount = statistics.get("notRecCount");
        double percentRec = statistics.get("percentRec");
        double avgHoursNotRec = statistics.get("avgLogHoursNotRec");
        double avgHoursRec = statistics.get("avgLogHoursRec");

        double avgPricePerHourNotRec = statistics.get("avgLogPricePerHourNotRec");
        double percentWinNotRec = statistics.get("percentWinNotRec");
        double percentMacNotRec = statistics.get("percentMacNotRec");
        double percentLinuxNotRec = statistics.get("percentLinuxNotRec");

        // Convert hours played to log form
        double HoursPlayed = Math.log(hoursPlayed);

        // Likelihood of hours played | Not Recommended
        double P_Hours_NotRecommended = avgHoursNotRec / (HoursPlayed * avgHoursRec);

        double notRecCount_given_H = (P_Hours_NotRecommended * notRecCount) / ((avgHoursNotRec / (HoursPlayed * avgHoursNotRec)) * notRecCount);
        double hoursPlayedNotRec = (notRecCount_given_H * P_Hours_NotRecommended) / notRecCount;

        // Likelihood of avgPricePerHour | Not Recommended
        double P_PricePerHours_NotRecommended = avgPricePerHourNotRec / (hourToPriceRatio + avgPricePerHourNotRec);
        double notRecCount_given_P_H = (P_PricePerHours_NotRecommended * notRecCount) / ((avgPricePerHourNotRec / (hourToPriceRatio + avgPricePerHourNotRec)) * notRecCount);
        double pricePerHourNotRec = (notRecCount_given_P_H * P_PricePerHours_NotRecommended) / notRecCount;

        // Likelihood of Windows | Not Recommended
        double likelihoodWinGivenNotRec;
        if (windows) {
            likelihoodWinGivenNotRec = percentWinNotRec / 100.0;
        } else {
            likelihoodWinGivenNotRec = 1 - (percentWinNotRec / 100.0);
        }

        // Likelihood of mac | Not Recommended
        double likelihoodMacGivenNotRec       ;
        if (mac) {
            likelihoodMacGivenNotRec = percentMacNotRec / 100.0;
        } else {
            likelihoodMacGivenNotRec = 1 - (percentMacNotRec / 100.0);
        }

        // Likelihood of Linux | Not Recommended
        double likelihoodLinuxGivenNotRec       ;
        if (linux) {
            likelihoodLinuxGivenNotRec = percentLinuxNotRec / 100.0;
        } else {
            likelihoodLinuxGivenNotRec = 1 - (percentLinuxNotRec / 100.0);
        }

        return (percentRec / 100) * hoursPlayedNotRec * pricePerHourNotRec * likelihoodWinGivenNotRec * likelihoodMacGivenNotRec * likelihoodLinuxGivenNotRec;
    }
}