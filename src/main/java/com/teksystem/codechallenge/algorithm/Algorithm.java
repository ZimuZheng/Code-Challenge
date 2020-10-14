package com.teksystem.codechallenge.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

public class Algorithm {


    private String[][] minRange;

    /**This function is used for public input zip code to get the least number range
     * Store the result in private
     * @param range
     */
    public void setMinRange(String[][] range) {
        minRange = findMinNumberRange(range);
    }

    /** This function is used for find minimum number range of input zip code <br>
     * The input zip code should be validated first <br>
     * The output range should contains the least number range to cover the input <br>
     * @param zipCode
     * @return new zip code range
     */
    private String[][] findMinNumberRange(String[][] zipCode) {

        // Pre-condition: the input zip code should be validated first
        assert zipCodeVerify(zipCode);

        if (zipCode.length == 0) {
            return new String[][]{};
        }

        // Cast data type from String to Integer from modify
        Integer[][] zipNumber = new Integer[zipCode.length][2];
        for (int i=0; i<zipCode.length; i++) {
            zipNumber[i][0] = Integer.parseInt(zipCode[i][0]);
            zipNumber[i][1] = Integer.parseInt(zipCode[i][1]);
        }

        // Use the first zip code in each range as key to sort
        Arrays.sort(zipNumber, Comparator.comparingInt(o -> o[0]));

        // Use new ArrayList to store each range
        // If the first zip code in next range is smaller than the second zip code in current range,
        // merge the two range into one range
        // If the first zip code in next range is bigger than the second zip code in current range,
        // store the current range in ArrayList and start a new range
        ArrayList<ArrayList> range = new ArrayList<>();
        ArrayList<Integer> tempRange = new ArrayList<>();
        tempRange.add(zipNumber[0][0]);
        tempRange.add(zipNumber[0][1]);

        for (int i=1; i<zipNumber.length;i++) {

            // First zip code in next range is bigger than the second zip code in current range
            if (tempRange.get(1) < zipNumber[i][0]-1) {
                range.add(tempRange);
                tempRange = new ArrayList<>();
                tempRange.add(zipNumber[i][0]);
                tempRange.add(zipNumber[i][1]);
            }

            // First zip code in next range is smaller than the second zip code in current range
            else {
                if (tempRange.get(1) < zipNumber[i][1]) {
                    tempRange.remove(1);
                    tempRange.add(zipNumber[i][1]);
                }
            }
        }

        // Add the last range in ArrayList
        range.add(tempRange);

        // Change the data type back to String
        // Make sure each zip code must have 5 digits
        // Put "0" in the front if any String contains less than 5 digits
        String[][] minNumberRange = new String[range.size()][2];
        for (int i=0; i<range.size(); i++) {
            minNumberRange[i][0] = String.format("%5s",range.get(i).get(0).toString()).replaceAll("\\s","0");
            minNumberRange[i][1] = String.format("%5s",range.get(i).get(1).toString()).replaceAll("\\s","0");
        }

        return minNumberRange;
    }


    /** This function is used for input zip code verification <br>
     * The input zip code should be String type only <br>
     * Each zip code range should have exact 2 zip code <br>
     * Each zip code should have exact 5 digits <br>
     * Each zip code should only contain numbers <br>
     * The second zip code should not be smaller than the first <br>
     * If the input range satisfied the condition, return true. <br>
     * Otherwise return false <br>
     * @param zipCode
     * @return validation result
     */
    private boolean zipCodeVerify(String[][] zipCode) {

        // Pre-condition: the input object cannot be null
        assert zipCode != null;

        // Go through each zip code range and validate
        for (int i=0; i<zipCode.length; i++) {

            // Validate the range contains exact 2 zip code
            if (zipCode[i].length != 2) {
                return false;
            }

            // Validate each zip code contains exact 5 digits
            if (zipCode[i][0].length() != 5 || zipCode[i][1].length() != 5) {
                return false;
            }

            // Validate each zip code contains only numbers
            Pattern pattern = Pattern.compile("[0-9]*");
            if (!pattern.matcher(zipCode[i][0]).matches() || !pattern.matcher(zipCode[i][1]).matches()) {
                return false;
            }

            // Validate the second zip code is not smaller than the first one
            if (Integer.parseInt(zipCode[i][1]) < Integer.parseInt(zipCode[i][0])) {
                return false;
            }
        }
        return true;
    }


    @Override
    public String toString() {

        if (minRange.length == 0) {
            return "[]";
        }

        StringBuilder ts = new StringBuilder();
        ts.append("[");
        for (int i=0; i<minRange.length; i++) {
            ts.append("[" + minRange[i][0] + ", " + minRange[i][1] + "], ");
        }
        ts.delete(ts.length()-2,ts.length());
        ts.append("]");
        return ts.toString();
    }
}
