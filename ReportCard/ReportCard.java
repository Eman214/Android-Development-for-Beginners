package com.example.android.miwok;

public class ReportCard {

    //String value for student name
    private String mName;

    //double value to store a class grade
    private double mBiologyGrade;

    //double value to store a class grade
    private double mEnglishGrade;

    //double value to store a class grade
    private double mHistoryGrade;

    //double value to store a class grade
    private double mMathGrade;

    /**
     * Constructs the report card object.
     */
    public ReportCard() {
        mName = "";
        mBiologyGrade = 0;
        mEnglishGrade = 0;
        mHistoryGrade = 0;
        mMathGrade = 0;
    }

    /**
     * Sets the string value for the variable
     *
     * @param name is the updated name to display
     */
    public void setmName(String name){
        mName = name;
    }

    /**
     * Sets the double value for the variable
     *
     * @param biologyGrade is the updated grade to display
     */
    public void setmBiologyGrade(double biologyGrade) {
        mBiologyGrade = biologyGrade;
    }

    /**
     * Sets the double value for the variable
     *
     * @param englishGrade is the updated grade to display
     */
    public void setmEnglishGrade(double englishGrade){
        mEnglishGrade = englishGrade;
    }

    /**
     * Sets the double value for the variable
     *
     * @param historyGrade is the updated grade to display
     */
    public void setmHistoryGrade(double historyGrade){
        mHistoryGrade = historyGrade;
    }

    /**
     * Sets the double value for the variable
     *
     * @param mathGrade is the updated grade to display
     */
    public void setmMathGrade(double mathGrade){
        mMathGrade = mathGrade;
    }

    /**
     * Gets the name of the student
     *
     * @return current name
     */
    public String getName(){
        return mName;
    }

    /**
     * Gets the double value
     *
     * @return current class grade
     */
    public double getBiologyGrade(){
        return mBiologyGrade;
    }

    /**
     * Gets the double value
     *
     * @return current class grade
     */
    public double getmEnglishGrade(){
        return mEnglishGrade;
    }

    /**
     * Gets the double value
     *
     * @return current class grade
     */
    public double getmHistoryGrade(){
        return mHistoryGrade;
    }

    /**
     * Gets the double value
     *
     * @return current class grade
     */
    public double getmMathGrade(){
        return mMathGrade;
    }

    @Override
    public String toString() {
        //Your code here!  Return a representation of
        //the report card rather than the empty string
        return "Name: " + mName
                + "\nBiology Grade: " + mBiologyGrade
                + "\nEnglish Grade: " + mEnglishGrade
                + "\nHistory Grade: " + mHistoryGrade
                + "\nMath Grade: " + mMathGrade;
    }
}
